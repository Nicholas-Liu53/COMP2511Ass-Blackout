package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import java.time.LocalTime;

import test.test_helpers.DummyConnection;
import test.test_helpers.ResponseHelper;
import test.test_helpers.TestHelper;

@TestInstance(value = Lifecycle.PER_CLASS)
public class Task2And3MyTests {
    @Test
    public void testGivenMobileXPhoneSimulation() { 
        // Test MobileXPhone features
        /*
            Test is identical to simulation from course
            Link to simulation: https://cgi.cse.unsw.edu.au/~cs2511/21T2/ass01/UI/app.py/simulate?raw=eyJzYXRlbGxpdGVzIjogWwp7ImlkIjogIk5hc2EiLCAicG9zaXRpb24iOiAwLCAidmVsb2NpdHkiOiAxNDEuNjcsICJwb3NzaWJsZUNvbm5lY3Rpb25zIjogW10sICJ0eXBlIjogIkJsdWVPcmlnaW5TYXRlbGxpdGUiLCAiY29ubmVjdGlvbnMiOiBbXSwgImhlaWdodCI6IDEwMDAwfSwKeyJpZCI6ICJTcGFjZVgiLCAicG9zaXRpb24iOiAxMDAsICJ2ZWxvY2l0eSI6IDU1LjUsICJwb3NzaWJsZUNvbm5lY3Rpb25zIjogW10sICJ0eXBlIjogIlNwYWNlWFNhdGVsbGl0ZSIsICJjb25uZWN0aW9ucyI6IFtdLCAiaGVpZ2h0IjogMTAwMDB9Cl0sICJjdXJyZW50VGltZSI6ICIwMDowMCIsIAoiZGV2aWNlcyI6IFsKeyJpc0Nvbm5lY3RlZCI6IGZhbHNlLCAiaWQiOiAiRGV2aWNlTW9iaWxlWCIsICJwb3NpdGlvbiI6IDUwLCAiYWN0aXZhdGlvblBlcmlvZHMiOiBbeyJzdGFydFRpbWUiOiAiMDA6MDAiLCAiZW5kVGltZSI6ICIwNjo0MCJ9XSwgInR5cGUiOiAiTW9iaWxlWFBob25lIn0sCnsiaXNDb25uZWN0ZWQiOiBmYWxzZSwgImlkIjogIkRldmljZUhhbmRoZWxkIiwgInBvc2l0aW9uIjogNDAsICJhY3RpdmF0aW9uUGVyaW9kcyI6IFt7InN0YXJ0VGltZSI6ICIwMDowMCIsICJlbmRUaW1lIjogIjA2OjQwIn1dLCAidHlwZSI6ICJIYW5kaGVsZERldmljZSJ9Cl19
            CLI commands:
            > createDevice MobileXPhone DeviceMobileX 50.00
            <
            > scheduleDeviceActivation DeviceMobileX 00:00 400
            <
            > createDevice HandheldDevice DeviceHandheld 40.00
            <
            > scheduleDeviceActivation DeviceHandheld 00:00 400
            <
            > createSatellite BlueOriginSatellite BlueOrigin 10000.00 0.00
            <
            > createSatellite SpaceXSatellite SpaceX 10000.00 100.00
            <
            > showWorldState
            < { <WorldState> }
            > simulate 1440
            <
            > showWorldState
            < { <WorldState> }
        */
        String initially = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("BlueOriginSatellite", "BlueOrigin", 10000, 0, 141.67, new String[] { "DeviceHandheld", "DeviceMobileX" })
            .expectSatellite("SpaceXSatellite", "SpaceX", 10000, 100, 55.5, new String[] { "DeviceHandheld", "DeviceMobileX" })
            .expectDevice(
                "HandheldDevice", "DeviceHandheld", 40, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } }
            ).expectDevice(
                "MobileXPhone", "DeviceMobileX", 50, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } }
            ).toString();

        String oneDayAfter = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite(
                "BlueOriginSatellite", "BlueOrigin", 10000, 20.40, 141.67,
                new String[] { "DeviceHandheld", "DeviceMobileX" },
                new DummyConnection[] {
                    new DummyConnection("DeviceHandheld", LocalTime.of(0, 0), LocalTime.of(6, 41), 399)
                }
            ).expectSatellite(
                "SpaceXSatellite", "SpaceX", 10000, 107.99, 55.50,
                new String[] { "DeviceHandheld", "DeviceMobileX" },
                new DummyConnection[] {
                    new DummyConnection("DeviceMobileX", LocalTime.of(0, 0), LocalTime.of(6, 41), 400)
                }
            ).expectDevice(
                "HandheldDevice", "DeviceHandheld", 40, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } }
            ).expectDevice(
                "MobileXPhone", "DeviceMobileX", 50, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } }
            ).toString();

        TestHelper plan = new TestHelper().createDevice("MobileXPhone", "DeviceMobileX", 50.00)
            .scheduleDeviceActivation("DeviceMobileX", LocalTime.of(0, 0), 400)
            .createDevice("HandheldDevice", "DeviceHandheld", 40.00)
            .scheduleDeviceActivation("DeviceHandheld", LocalTime.of(0, 0), 400)
            .createSatellite("BlueOriginSatellite", "BlueOrigin", 10000.00, 0.00)
            .createSatellite("SpaceXSatellite", "SpaceX", 10000.00, 100.00)
            .showWorldState(initially)
            .simulate(1440)
            .showWorldState(oneDayAfter);
        plan.executeTestPlan();
    }

    @Test
    public void testGivenAWSCloudServerSimulation() {
        // Test AWSCloudServer features
        /*
            Test is identical to simulation from course
            Link to simulation: https://cgi.cse.unsw.edu.au/~cs2511/21T2/ass01/UI/app.py/simulate?raw=eyJzYXRlbGxpdGVzIjpbeyJpZCI6IkEiLCJwb3NpdGlvbiI6MTIwLCJ2ZWxvY2l0eSI6MTQxLjY2NjY2NjY2NjY2NjY2LCJwb3NzaWJsZUNvbm5lY3Rpb25zIjpbIkFXUyJdLCJ0eXBlIjoiQmx1ZU9yaWdpblNhdGVsbGl0ZSIsImNvbm5lY3Rpb25zIjpbXSwiaGVpZ2h0IjoxMDAwMH0seyJpZCI6IkIiLCJwb3NpdGlvbiI6MjAsInZlbG9jaXR5IjoxNDEuNjY2NjY2NjY2NjY2NjYsInBvc3NpYmxlQ29ubmVjdGlvbnMiOlsiQVdTIl0sInR5cGUiOiJCbHVlT3JpZ2luU2F0ZWxsaXRlIiwiY29ubmVjdGlvbnMiOltdLCJoZWlnaHQiOjEwMDAwfSx7ImlkIjoiQyIsInBvc2l0aW9uIjozNTAsInZlbG9jaXR5IjoxNDEuNjY2NjY2NjY2NjY2NjYsInBvc3NpYmxlQ29ubmVjdGlvbnMiOlsiQVdTIl0sInR5cGUiOiJCbHVlT3JpZ2luU2F0ZWxsaXRlIiwiY29ubmVjdGlvbnMiOltdLCJoZWlnaHQiOjEwMDAwfV0sImN1cnJlbnRUaW1lIjoiMDA6MDAiLCJkZXZpY2VzIjpbeyJpc0Nvbm5lY3RlZCI6ZmFsc2UsImlkIjoiQVdTIiwicG9zaXRpb24iOjQwLjUsImFjdGl2YXRpb25QZXJpb2RzIjpbeyJzdGFydFRpbWUiOiIwMDowMCIsImVuZFRpbWUiOiIyMzoyMCJ9XSwidHlwZSI6IkFXU0Nsb3VkU2VydmVyIn1dLCJwb3NpdGlvbnMiOnt9fQ==
            CLI commands:
            > createDevice AWSCloudServer AWS 40.50
            <
            > scheduleDeviceActivation AWS 00:00 1400
            <
            > createSatellite BlueOriginSatellite A 10000.00 120.00
            <
            > createSatellite BlueOriginSatellite B 10000.00 20.00
            <
            > createSatellite BlueOriginSatellite C 10000.00 350.00
            <
            > showWorldState
            < { <WorldState> }
            > simulate 1440
            <
            > showWorldState
            < { <WorldState> }
        */
        String initially = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("BlueOriginSatellite", "A", 10000, 120, 141.67, new String[] { "AWS" })
            .expectSatellite("BlueOriginSatellite", "B", 10000, 20, 141.67, new String[] { "AWS" })
            .expectSatellite("BlueOriginSatellite", "C", 10000, 350, 141.67, new String[] { "AWS" })
            .expectDevice(
                "AWSCloudServer", "AWS", 40.50, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 20) } }
            ).toString();

        String oneDayAfter = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite(
                "BlueOriginSatellite", "A", 10000, 140.40, 141.67, 
                new String[] { },
                new DummyConnection[] {
                    new DummyConnection("AWS", LocalTime.of(0,0), LocalTime.of(2, 12), 126)
                }
            ).expectSatellite(
                "BlueOriginSatellite", "B", 10000, 40.40, 141.67, 
                new String[] { "AWS" },
                new DummyConnection[] {
                    new DummyConnection("AWS", LocalTime.of(0,0), LocalTime.of(23, 21), 1395)
                }
            ).expectSatellite(
                "BlueOriginSatellite", "C", 10000, 10.40, 141.67, 
                new String[] { "AWS" },
                new DummyConnection[] {
                    new DummyConnection("AWS", LocalTime.of(2,12), LocalTime.of(23, 21), 1263)
                }
            ).expectDevice(
                "AWSCloudServer", "AWS", 40.50, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 20) } }
            ).toString();

        TestHelper plan = new TestHelper().createDevice("AWSCloudServer", "AWS", 40.50)
            .scheduleDeviceActivation("AWS", LocalTime.of(0, 0), 1400)
            .createSatellite("BlueOriginSatellite", "A", 10000.00, 120.00)
            .createSatellite("BlueOriginSatellite", "B", 10000.00, 20.00)
            .createSatellite("BlueOriginSatellite", "C", 10000.00, 350.00)
            .showWorldState(initially)
            .simulate(1440)
            .showWorldState(oneDayAfter);
        plan.executeTestPlan();
    }

    @Test
    public void testInside3040RegionConnectForNasa() {
        // Tests if a device in the region [30,40] connects to the Nasa Satellite
        // if the Nasa Satellite already has 6 connections
        /* 
            CLI commands:
            > createDevice LaptopDevice Laptop1 275.00
            < 
            > scheduleDeviceActivation Laptop1 00:00 1439
            <
            > createDevice DesktopDevice Desktop1 290.00
            < 
            > scheduleDeviceActivation Desktop1 00:00 1439
            <
            > createDevice LaptopDevice Laptop2 305.00
            < 
            > scheduleDeviceActivation Laptop2 00:00 1439
            <
            > createDevice DesktopDevice Desktop2 320.00
            < 
            > scheduleDeviceActivation Desktop2 00:00 1439
            <
            > createDevice LaptopDevice Laptop3 335.00
            <
            > scheduleDeviceActivation Laptop3 00:00 1439
            <
            > createDevice DesktopDevice Desktop3 350.00
            <
            > scheduleDeviceActivation Desktop3 00:00 1439
            <
            > createDevice DesktopDevice Desktop4inthezone 40.00
            <
            > scheduleDeviceActivation Desktop4inthezone 00:00 1439
            <
            > createSatellite NasaSatellite Nasa 8000.00 310.00
            <
            > showWorldState
            < { <WorldState> }
            > simulate 1440
            <
            > showWorldState
            < { <WorldState> }
        */
        String initially = new ResponseHelper(LocalTime.of(0,0))
            .expectSatellite(
                "NasaSatellite", "Nasa", 8000, 310, 85, 
                new String[] {
                    "Desktop1", "Desktop2", "Desktop3", "Laptop1", "Laptop2", "Laptop3"
                }
            ).expectDevice(
                "DesktopDevice", "Desktop1", 290, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop2", 320, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop3", 350, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop4inthezone", 40, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop1", 275, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop2", 305, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop3", 335, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).toString();
        
        String oneDayAfter = new ResponseHelper(LocalTime.of(0,0))
            .expectSatellite(
                "NasaSatellite", "Nasa", 8000, 325.3, 85, 
                new String[] {
                    "Desktop1", "Desktop2", "Desktop3", "Desktop4inthezone", "Laptop1", "Laptop2", "Laptop3"
                }, 
                new DummyConnection[] {
                    new DummyConnection("Desktop1", LocalTime.of(0, 0), LocalTime.of(16, 57), 1007),
                    new DummyConnection("Desktop2", LocalTime.of(0, 0), 1429),
                    new DummyConnection("Desktop3", LocalTime.of(0, 0), 1429),
                    new DummyConnection("Laptop1", LocalTime.of(0, 0), 1429),
                    new DummyConnection("Laptop2", LocalTime.of(0, 0), 1429),
                    new DummyConnection("Laptop3", LocalTime.of(0, 0), 1429),
                    new DummyConnection("Desktop4inthezone", LocalTime.of(16, 57), 412),
                }
            ).expectDevice(
                "DesktopDevice", "Desktop1", 290, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop2", 320, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop3", 350, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop4inthezone", 40, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop1", 275, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop2", 305, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop3", 335, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).toString();
        
        TestHelper plan = new TestHelper()
            .createDevice("LaptopDevice", "Laptop1", 275)
            .scheduleDeviceActivation("Laptop1", LocalTime.of(0,0), 1439)
            .createDevice("DesktopDevice", "Desktop1", 290)
            .scheduleDeviceActivation("Desktop1", LocalTime.of(0,0), 1439)
            .createDevice("LaptopDevice", "Laptop2", 305)
            .scheduleDeviceActivation("Laptop2", LocalTime.of(0,0), 1439)
            .createDevice("DesktopDevice", "Desktop2", 320)
            .scheduleDeviceActivation("Desktop2", LocalTime.of(0,0), 1439)
            .createDevice("LaptopDevice", "Laptop3", 335)
            .scheduleDeviceActivation("Laptop3", LocalTime.of(0,0), 1439)
            .createDevice("DesktopDevice", "Desktop3", 350)
            .scheduleDeviceActivation("Desktop3", LocalTime.of(0,0), 1439)
            .createDevice("DesktopDevice", "Desktop4inthezone", 40)
            .scheduleDeviceActivation("Desktop4inthezone", LocalTime.of(0,0), 1439)
            .createSatellite("NasaSatellite", "Nasa", 8000, 310)
            .showWorldState(initially)
            .simulate(1440)
            .showWorldState(oneDayAfter);
        plan.executeTestPlan();
    }

    @Test
    public void testOutside3040RegionNoConnectForNasa() {
        // Tests if a device in the region [30,40] connects to the Nasa Satellite
        // if the Nasa Satellite already has 6 connections
        /* 
            CLI commands:
            > createDevice LaptopDevice Laptop1 275.00
            < 
            > scheduleDeviceActivation Laptop1 00:00 1439
            <
            > createDevice DesktopDevice Desktop1 290.00
            < 
            > scheduleDeviceActivation Desktop1 00:00 1439
            <
            > createDevice LaptopDevice Laptop2 305.00
            < 
            > scheduleDeviceActivation Laptop2 00:00 1439
            <
            > createDevice DesktopDevice Desktop2 320.00
            < 
            > scheduleDeviceActivation Desktop2 00:00 1439
            <
            > createDevice LaptopDevice Laptop3 335.00
            <
            > scheduleDeviceActivation Laptop3 00:00 1439
            <
            > createDevice DesktopDevice Desktop3 350.00
            <
            > scheduleDeviceActivation Desktop3 00:00 1439
            <
            > createDevice DesktopDevice Desktop4notinthezone 41.00
            <
            > scheduleDeviceActivation Desktop4notinthezone 00:00 1439
            <
            > createSatellite NasaSatellite Nasa 8000.00 310.00
            <
            > showWorldState
            < { <WorldState> }
            > simulate 1440
            <
            > showWorldState
            < { <WorldState> }
        */
        String initially = new ResponseHelper(LocalTime.of(0,0))
            .expectSatellite(
                "NasaSatellite", "Nasa", 8000, 310, 85, 
                new String[] {
                    "Desktop1", "Desktop2", "Desktop3", "Laptop1", "Laptop2", "Laptop3"
                }
            ).expectDevice(
                "DesktopDevice", "Desktop1", 290, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop2", 320, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop3", 350, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop4notinthezone", 41, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop1", 275, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop2", 305, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop3", 335, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).toString();
        
        String oneDayAfter = new ResponseHelper(LocalTime.of(0,0))
            .expectSatellite(
                "NasaSatellite", "Nasa", 8000, 325.3, 85, 
                new String[] {
                    "Desktop1", "Desktop2", "Desktop3", "Desktop4notinthezone", "Laptop1", "Laptop2", "Laptop3"
                }, 
                new DummyConnection[] {
                    new DummyConnection("Desktop1", LocalTime.of(0, 0), 1429),
                    new DummyConnection("Desktop2", LocalTime.of(0, 0), 1429),
                    new DummyConnection("Desktop3", LocalTime.of(0, 0), 1429),
                    new DummyConnection("Laptop1", LocalTime.of(0, 0), 1429),
                    new DummyConnection("Laptop2", LocalTime.of(0, 0), 1429),
                    new DummyConnection("Laptop3", LocalTime.of(0, 0), 1429),
                }
            ).expectDevice(
                "DesktopDevice", "Desktop1", 290, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop2", 320, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop3", 350, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop4notinthezone", 41, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop1", 275, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop2", 305, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop3", 335, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).toString();
        
        TestHelper plan = new TestHelper()
            .createDevice("LaptopDevice", "Laptop1", 275)
            .scheduleDeviceActivation("Laptop1", LocalTime.of(0,0), 1439)
            .createDevice("DesktopDevice", "Desktop1", 290)
            .scheduleDeviceActivation("Desktop1", LocalTime.of(0,0), 1439)
            .createDevice("LaptopDevice", "Laptop2", 305)
            .scheduleDeviceActivation("Laptop2", LocalTime.of(0,0), 1439)
            .createDevice("DesktopDevice", "Desktop2", 320)
            .scheduleDeviceActivation("Desktop2", LocalTime.of(0,0), 1439)
            .createDevice("LaptopDevice", "Laptop3", 335)
            .scheduleDeviceActivation("Laptop3", LocalTime.of(0,0), 1439)
            .createDevice("DesktopDevice", "Desktop3", 350)
            .scheduleDeviceActivation("Desktop3", LocalTime.of(0,0), 1439)
            .createDevice("DesktopDevice", "Desktop4notinthezone", 41)
            .scheduleDeviceActivation("Desktop4notinthezone", LocalTime.of(0,0), 1439)
            .createSatellite("NasaSatellite", "Nasa", 8000, 310)
            .showWorldState(initially)
            .simulate(1440)
            .showWorldState(oneDayAfter);
        plan.executeTestPlan();
    }

    @Test
    public void maxLaptopsAndDesktopsForBlueOrigin() {
        // Tests if there isn't too many laptop and desktop
        // connections for the Blue Origin Satellite 
        /* 
            CLI commands:
            > createDevice LaptopDevice Laptop1 275.00
            < 
            > scheduleDeviceActivation Laptop1 00:00 1439
            <
            > createDevice DesktopDevice Desktop1 290.00
            < 
            > scheduleDeviceActivation Desktop1 00:00 1439
            <
            > createDevice LaptopDevice Laptop2 305.00
            < 
            > scheduleDeviceActivation Laptop2 00:00 1439
            <
            > createDevice DesktopDevice Desktop2 320.00
            < 
            > scheduleDeviceActivation Desktop2 00:00 1439
            <
            > createDevice LaptopDevice Laptop3 335.00
            <
            > scheduleDeviceActivation Laptop3 00:00 1439
            <
            > createDevice DesktopDevice Desktop3 350.00
            <
            > scheduleDeviceActivation Desktop3 00:00 1439
            <
            > createDevice LaptopDevice Laptop4 5.00
            <
            > scheduleDeviceActivation Laptop4 00:00 1439
            <
            > createDevice LaptopDevice Laptop5 10.00
            <
            > scheduleDeviceActivation Laptop5 00:00 1439
            <
            > createDevice LaptopDevice Laptop6 20.00
            <
            > scheduleDeviceActivation Laptop6 00:00 1439
            <
            > createSatellite BlueOriginSatellite BlueOrigin 8000.00 310.00
            <
            > showWorldState
            < { <WorldState> }
            > simulate 1440
            <
            > showWorldState
            < { <WorldState> }
        */
        String initially = new ResponseHelper(LocalTime.of(0,0))
            .expectSatellite(
                "BlueOriginSatellite", "BlueOrigin", 8000, 310, 141.66, 
                new String[] {
                    "Desktop1", "Desktop2", "Desktop3", "Laptop1", "Laptop2",
                    "Laptop3", "Laptop4", "Laptop5", "Laptop6"
                }
            ).expectDevice(
                "DesktopDevice", "Desktop1", 290, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop2", 320, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop3", 350, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop1", 275, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop2", 305, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop3", 335, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop4", 5, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop5", 10, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop6", 20, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).toString();
        
        String oneDayAfter = new ResponseHelper(LocalTime.of(0,0))
            .expectSatellite(
                "BlueOriginSatellite", "BlueOrigin", 8000, 335.5, 141.66, 
                new String[] {
                    "Desktop1", "Desktop2", "Desktop3", "Laptop1", "Laptop2",
                    "Laptop3", "Laptop4", "Laptop5", "Laptop6"
                },
                new DummyConnection[] {
                    new DummyConnection("Desktop1", LocalTime.of(0, 0), 1434),
                    new DummyConnection("Desktop2", LocalTime.of(0, 0), 1434),
                    new DummyConnection("Laptop1", LocalTime.of(0, 0), 1437),
                    new DummyConnection("Laptop2", LocalTime.of(0, 0), 1437),
                    new DummyConnection("Laptop3", LocalTime.of(0, 0), 1437),
                    new DummyConnection("Laptop4", LocalTime.of(0, 0), 1437),
                    new DummyConnection("Laptop5", LocalTime.of(0, 0), 1437)
                }
            ).expectDevice(
                "DesktopDevice", "Desktop1", 290, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop2", 320, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "DesktopDevice", "Desktop3", 350, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop1", 275, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop2", 305, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop3", 335, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop4", 5, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop5", 10, true,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).expectDevice(
                "LaptopDevice", "Laptop6", 20, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(23, 59) } }
            ).toString();
        
        TestHelper plan = new TestHelper()
            .createDevice("LaptopDevice", "Laptop1", 275)
            .scheduleDeviceActivation("Laptop1", LocalTime.of(0,0), 1439)
            .createDevice("DesktopDevice", "Desktop1", 290)
            .scheduleDeviceActivation("Desktop1", LocalTime.of(0,0), 1439)
            .createDevice("LaptopDevice", "Laptop2", 305)
            .scheduleDeviceActivation("Laptop2", LocalTime.of(0,0), 1439)
            .createDevice("DesktopDevice", "Desktop2", 320)
            .scheduleDeviceActivation("Desktop2", LocalTime.of(0,0), 1439)
            .createDevice("LaptopDevice", "Laptop3", 335)
            .scheduleDeviceActivation("Laptop3", LocalTime.of(0,0), 1439)
            .createDevice("DesktopDevice", "Desktop3", 350)
            .scheduleDeviceActivation("Desktop3", LocalTime.of(0,0), 1439)
            .createDevice("LaptopDevice", "Laptop4", 5)
            .scheduleDeviceActivation("Laptop4", LocalTime.of(0,0), 1439)
            .createDevice("LaptopDevice", "Laptop5", 10)
            .scheduleDeviceActivation("Laptop5", LocalTime.of(0,0), 1439)
            .createDevice("LaptopDevice", "Laptop6", 20)
            .scheduleDeviceActivation("Laptop6", LocalTime.of(0,0), 1439)
            .createSatellite("BlueOriginSatellite", "BlueOrigin", 8000, 310)
            .showWorldState(initially)
            .simulate(1440)
            .showWorldState(oneDayAfter);
        plan.executeTestPlan();
    }
}