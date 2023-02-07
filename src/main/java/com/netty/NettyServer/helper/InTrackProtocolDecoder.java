package com.netty.NettyServer.helper;

import java.util.Arrays;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class InTrackProtocolDecoder {
    //$,SPAI,IN,A2,860902045484299,1,13072022,141900,015.076885,N,075.948543,E,000.13,135.95,0222.40,07,404, 45,ABCD,EFGH,17,-127,ABCD,EFGH;-127,ABCD,EFGH;-127,ABCD,EFGH;-127,ABCD,EFGH,100,100,0123456789,0123456789,0123456789,0123456789,0123456789,0123456789,ABCD,*
    private static final Pattern PATTERN = new PatternBuilder()
            .text("$,")     // header
            .expression("[^,]*,")                // manufacture-Id
            .expression("([^,]+),")              // Packet type
            .expression("[^,]+,")              // Device type
            .number("(d{15}),")                  // imei
            .expression("([012]),")               // gps fix
            .number("(dd),?(dd),?(d{2,4}),?")    // date (ddmmyyyy)
            .number("(dd),?(dd),?(dd),")         // time (hhmmss)
            .number("(d+.d+),([NS]),")           // latitude
            .number("(d+.d+),([EW]),")           // longitude
            .number("(d+.?d*),")                 // speed
            .number("(d+.?d*),")                 // course
            .number("(d+.?d*),")                 // altitude
            .number("(d+),")                     // satellites
            .expression(".*,")                 // cells
            .number("(d+),")                      // batt %
            .number("(d+),")                      // fuel %
            .number("(d+),")                      // sensor-1
            .number("(d+),")                      // sensor-2
            .number("(d+),")                      // sensor-3
            .number("(d+),")                      // sensor-4
            .number("(d+),")                      // sensor-5
            .number("(d+),")                      // sensor-6
            .number("x+,")                       // checksum
            .text("*")
            .any()
            .compile();

    /**
     * First method called from Parent ChannelInboundHandler read method.
     * This method check whether packet is basic concox or extended concox packet.
     *
     * @throws Exception
     */

    public void decode(String str) throws Exception {

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Parser parser = new Parser(PATTERN, str);
        if (!parser.matches()) {
            System.out.println("Parser Failed");
            return;
        }
        System.out.println("Packet Type - " + parser.next());
        System.out.println("Imei - " + parser.next());
        System.out.println("Gps fix - " + parser.next().equals("1"));
        System.out.println("Added : " + parser.nextDateTime(Parser.DateTimeFormat.DMY_HMS));
        double latitude = parser.nextCoordinate(Parser.CoordinateFormat.DEG_HEM);
        double longitude = parser.nextCoordinate(Parser.CoordinateFormat.DEG_HEM);
        System.out.println("Location : " + Arrays.toString(new double[]{latitude, longitude}));
        System.out.println("Speed : " + parser.nextDouble().intValue() + "km/h");
        System.out.println("Direction : " + parser.nextDouble().intValue());
        System.out.println("Altitude : " + parser.nextDouble());//Altitude Ignore
        System.out.println("Satellites : " + parser.nextInt());
        System.out.println("Battery : " + parser.nextInt());
        System.out.println("Fuel : " + parser.nextInt());
        System.out.println("sensor-1 - " + parser.next());
        System.out.println("sensor-2 - " + parser.next());
        System.out.println("sensor-3 - " + parser.next());
        System.out.println("sensor-4 - " + parser.next());
        System.out.println("sensor-5 - " + parser.next());
        System.out.println("sensor-6 - " + parser.next());
    }

    public static void main(String[] args) throws Exception {
        InTrackProtocolDecoder inTrackProtocolDecoder = new InTrackProtocolDecoder();
        String str = "$,SPAI,IN,A2,860902045484299,1,13072022,141900,015.076885,N,075.948543,E,000.13,135.95,0222.40,07,404, 45,ABCD,EFGH,17,-127,ABCD,EFGH;-127,ABCD,EFGH;-127,ABCD,EFGH;-127,ABCD,EFGH,100,100,0123456789,0123456789,0123456789,0123456789,0123456789,0123456789,ABCD,*";
        inTrackProtocolDecoder.decode(str);
    }
}
