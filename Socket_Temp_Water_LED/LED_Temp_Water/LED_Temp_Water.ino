#define ON 1
#define OFF 0
#include <OneWire.h>

//  자바에서 1혹은 0을 입력하면 LED의 on/off 제어
//  자바와 시리얼 통신을 통해, '1'이 입력되면 LED가 on, '0'이 입력되면 LED가 off된다.

int sensor_in = A0; 
int DS18S20_Pin = 2;                // 온도 센서 2번 핀으로 연결
OneWire ds(DS18S20_Pin);

void setup() {
  Serial.begin(115200);
  pinMode(13,OUTPUT);
  pinMode(sensor_in, INPUT);
}

void LED_OnOff(int state)
{
  digitalWrite(13,state);
}

void serialEvent()
{
  int c = Serial.read();
  if (c == '1')
  {
    LED_OnOff(ON);
  }
  else if (c == '0')
  {
    LED_OnOff(OFF);
  }
}

void loop(){  
  float temperature = getTemp();
  int sensorValue = analogRead(A0);
  float water = sensorValue*(5.0/1024.0);

  Serial.print(temperature);
  Serial.print("*");
  Serial.println(water);
  delay(500);
}

float getTemp(){                                   //온도 측정 후 반환하는 함수
 byte data[12];
 byte addr[8];
 if ( !ds.search(addr)) {
   ds.reset_search();
   return -1000;
 }
 if ( OneWire::crc8( addr, 7) != addr[7]) {
   Serial.println("CRC is not valid!");
   return -1000;
 }
 if ( addr[0] != 0x10 && addr[0] != 0x28) {
   Serial.print("Device is not recognized");
   return -1000;
 }
 ds.reset();
 ds.select(addr);
 ds.write(0x44,1);                                   
 byte present = ds.reset();
 ds.select(addr);  
 ds.write(0xBE); 
 
 for (int i = 0; i < 9; i++) { 
  data[i] = ds.read();                                                          
 }
 
 ds.reset_search(); 
 byte MSB = data[1];
 byte LSB = data[0];
 float tempRead = ((MSB << 8) | LSB); 
 float TemperatureSum = tempRead / 16; 
 return TemperatureSum;                                                                    
}
