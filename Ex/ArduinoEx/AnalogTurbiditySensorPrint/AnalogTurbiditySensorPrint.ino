void setup() {
  Serial.begin(115200);
}

void loop() {
  int sensorValue = analogRead(A0);  // 아날로그 0번 핀에서 센서값 인식받아 저장
  float voltage = sensorValue * (5.0 / 1024.0);  // 전압 계산
  Serial.println(voltage);  // 시리얼 모니터에 출력
  delay(1000);  // 1초 대기
}
