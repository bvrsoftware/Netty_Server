package com.netty.NettyServer.helper;

import java.io.DataInputStream;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ParserTest {
    //[0,104,864547034536305,P1.B3.H3.F1.D11<LF><CR>1,H,0,0,211125,072512,28.610535,77.112122,0,0,0.0,0,0,64,255,6982,3864,1332,0,0.0,0,0,0,404,10,20E,32C,16,1<LF><CR>2,H,0,20,211125,072752,28.610535,77.112122,0,0,0.0,0,0.00,192,255,3823,3864,1332,0,0.0,0,0,0,404,10,20E,44B5,13,1<LF><CR>3,H,0,20,211125,072757,28.610535,77.112122,0,0,0.0,0,0.00,192,255,2799,3864,1332,0,0.0,0,0,0,404,10,20E,44B5,13,1<LF><CR>4,H,0,20,211125,072803,28.610535,77.112122,0,0,0.0,0,0.00,192,255,2799,3864,1332,0,0.0,0,0,0,404,10,20E,44B5,13,0<LF><CR>191,H,0,0,211125,163900,28.610662,77.112000,900,63,1.8,0,0.22,64,255,6888,3464,1332,0,235.2,0,0,0,404,10,20E,44B5,12,1<LF>*30234]
    private static final Pattern PATTERN = new PatternBuilder()
            .text("[")
            .expression("([01]),")     //pkt_type
            .expression("[^,]+,")    //comm
            .number("(d{15}),")      //imi number
            .expression("[^,]*<")
            .text("LF>")
            .any()
            .compile();
    // End Header
    private static final Pattern RECORD = new PatternBuilder()
            .text("<CR>")
            .number("(d+),")//
            .expression("([HL]),")
            .number("d+,")
            .number("d+,")
            .number("(dd),?(dd),?(dd),")
            .number("(dd),?(dd),?(dd),")
            .number("(d+.d+),")               // latitude
            .number("(d+.d+),")              // longitude
            .number("d+,")              // Altitude in meter
            .number("d+,")              // Angle
            .number("(d+.d),")            //KM per Hour
            .number("d+,")              // Number of satellite seen by the vehicle
            .number("(d+),")             // Odometer Reading
            .number("(d+),")             // Digital Input status of the vehicle. See Input sheet for more information
            .number("(d+),")             // Digital Output status of the vehicle. See Output sheet for more information
            .number("(d+),")             // Vehicle Battery in milli volt
            .number("(d+),")             // Device internal battery in milli volt
            .number("(d+),")             // Analog input 1
            .number("(d+),")             // Analog input 2
            .number("(d+.d),")            //Temperature sensor value in degree centigrade
            .number("d+,")              // RFID code value read from RFID reader
            .number("d+,")              // Barcode value read from Barcode reader
            .number("d+,")              // Camera Picture ID value read from camera module
            .number("(d+),")             // Mobile Country code
            .number("(d+),")           // Mobile Network code
            .number("(x+),")             // Location Area Code in Hex format
            .number("(x+),")             // Mobile Cell ID in Hex Format
            .number("(d+),")             // Signal Strength 0=low 31=highest
            .number("(d+)")             // Network Status code
            .any()
            .compile();

    public void decode(String str) throws Exception {
        Parser parser = new Parser(RECORD, str);
        if (!parser.matches()) {
            System.out.println("Parser Failed");
            return;
        } else {
            System.out.println("Pass");
        }
        /*System.out.println("Packet Type - " + parser.next());
        System.out.println("Imei - " + parser.next());*/
        System.out.println("Record Number - " + parser.nextInt());
        System.out.println("Record Type - " + parser.next());
        System.out.println("Date - " + parser.nextDateTime());
        Double latitude = parser.nextDouble();
        Double longitude = parser.nextDouble();
        System.out.println("Location - " + Arrays.toString(new double[]{latitude, longitude}));
        System.out.println("Speed - " + parser.nextDouble() + " KM per Hour");
        System.out.println("Odometer Reading - " + parser.nextInt());
        System.out.println("Digital Input status - " + parser.nextInt());
        System.out.println("Digital Output status - " + parser.nextInt());
        System.out.println("Vehicle Battery - " + parser.nextInt());
        System.out.println("Device internal battery - " + parser.nextInt());
        System.out.println("Analog input 1 - " + parser.nextInt());
        System.out.println("Analog input 2 - " + parser.nextInt());
        System.out.println("Temperature sensor value - " + parser.nextDouble());
        System.out.println("Mobile Country code - " + parser.nextInt());
        System.out.println("Mobile Network code - " + parser.nextInt());
        System.out.println("Location Area Code in Hex format - " + parser.next());
        System.out.println("Mobile Cell ID in Hex Format - " + parser.next());
        System.out.println("Signal Strength - " + parser.nextInt());
        System.out.println("Network Status code - " + parser.nextInt());
    }

    public static void main(String[] args) throws Exception {
        DataInputStream dataInputStream = new DataInputStream(System.in);
        ParserTest parserTest = new ParserTest();
        while (true){
            System.out.println("Enter Packet : ");
            String str = dataInputStream.readLine();
            parserTest.decode(str);
        }
    }
}

