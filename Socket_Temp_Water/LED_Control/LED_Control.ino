int ledPin = 13;

void setup() {
  Serial.begin(115200);
  pinMode(ledPin, OUTPUT);

}

void loop() {
  int readValue = analogRead(A0);
  float voltage = readValue * 5.0 / 1024.0;
  float temperature = voltage * 100;

  if (Serial.available()){
    char ch = Serial.read();
    if (ch == '1'){
        digitalWrite(ledPin, HIGH);
    } else if(ch == '0'){
        digitalWrite(ledPin, LOW);
    }
  }

  Serial.print(temperature);
  Serial.println("*0");
  delay(1000);
}
