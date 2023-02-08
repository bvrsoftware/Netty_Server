package com.netty.NettyServer.server.In;

import com.netty.NettyServer.helper.Parser;
import com.netty.NettyServer.helper.PatternBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;
import java.util.regex.Pattern;


public class InChannelHandler extends SimpleChannelInboundHandler {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("hello client !!! ");
    }

    private static final Pattern LOG_HEART_HEADER = new PatternBuilder()
            .text("[")
            .number("([0123]),")     //pkt_type   0- Login ,1- Heart Beat ,2- Read/write 3-FOTO
            .expression("[^,]+,")    //comm
            .number("(d{15}),")      //imi number
            .expression("[^,]*")
            .any()
            .compile();
    // End Header
    private static final Pattern RECORD=new PatternBuilder()
            .text("<CR>")               //Record header
            .number("(d+),")            // Record Number
            .expression("([HL]),")      // Record History
            .number("d+,")              // PI
            .number("d+,")                 //RID
            .number("(dd),?(dd),?(dd),")    //Date
            .number("(dd),?(dd),?(dd),")    //Time
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
    // record end
    //Footer
    private static final Pattern FOOTER_PATTERN=new PatternBuilder()
            .text("*")
            .number("(d+)")
            .any()
            .compile();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        String str = (String) msg;
        String[] split = str.split("<LF>");
        Parser parser = new Parser(LOG_HEART_HEADER, split[0]);
        if(!parser.matches()){
            System.out.println("Parser Failed");
            return;
        }
        System.out.println("Packet Type - " + parser.nextInt()); //0 - log packet 1- Heart beat
        System.out.println("Imei - " + parser.next());
        for(int i=1;i<split.length-1;i++){
            System.out.println("**************** RECORD NUMBER "+i+" ******************");
            Parser recordParser = new Parser(RECORD, split[i]);
            if(!recordParser.matches()){
                System.out.println("Record Parser Failed -"+i);
                continue;
            }
            System.out.println("Record Number - " + recordParser.nextInt());
            System.out.println("Record Type - " + recordParser.next());
            System.out.println("Date - " + recordParser.nextDateTime());
            Double latitude = recordParser.nextDouble();
            Double longitude = recordParser.nextDouble();
            System.out.println("Location - " + Arrays.toString(new double[]{latitude, longitude}));
            System.out.println("Speed - " + recordParser.nextDouble() + " KM per Hour");
            System.out.println("Odometer Reading - " + recordParser.nextInt());
            System.out.println("Digital Input status - " + recordParser.nextInt());
            System.out.println("Digital Output status - " + recordParser.nextInt());
            System.out.println("Vehicle Battery - " + recordParser.nextInt());
            System.out.println("Device internal battery - " + recordParser.nextInt());
            System.out.println("Analog input 1 - " + recordParser.nextInt());
            System.out.println("Analog input 2 - " + recordParser.nextInt());
            System.out.println("Temperature sensor value - " + recordParser.nextDouble());
            System.out.println("Mobile Country code - " + recordParser.nextInt());
            System.out.println("Mobile Network code - " + recordParser.nextInt());
            System.out.println("Location Area Code in Hex format - " + recordParser.next());
            System.out.println("Mobile Cell ID in Hex Format - " + recordParser.next());
            System.out.println("Signal Strength - " + recordParser.nextInt());
            System.out.println("Network Status code - " + recordParser.nextInt());
        }

       Parser footer_parser = new Parser(FOOTER_PATTERN, split[split.length - 1]);
        if(footer_parser.matches()){
          ctx.writeAndFlush("Okay");
        }
        System.out.println("CRC - "+footer_parser.nextInt());
    }
}
