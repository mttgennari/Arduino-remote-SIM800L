#include <SoftwareSerial.h>
#include <EEPROM.h>
//Definiamo una SoftwareSerial con i pin 8 ed 7 che devono essere collegati rispettivamente al TX ed RX del SIM800L
// 
SoftwareSerial SIM800L(8, 7);

float getVoltage(int);

String lastLine="";
int str_len;
char lastCharRead;
int CASA = 5;
int PTEMP = 0;
char numero[11];

/*INIZIALIZZAZIONE DEL PROGRAMMA, CODICE ESEGUITO ALL'AVVIO*/

void setup() {
  //Inizializziamo la seriale dell'arduino verso il computer (fisica)
  Serial.begin(57600);
  while (!Serial);

  //Inizializziamo la seriale dell'arduino verso il SIM800L (software)
  SIM800L.begin(57600);
  delay(1000);

  Serial.println("Serial ok!");
  delay(10000);
  SIM800L.print("AT+CPIN=36912\r\n");
  delay(3000);
  SIM800L.print("AT+CMGF=1\r\n");
  delay(1000);
  SIM800L.print("AT+CNMI=1,2,0,0,0\r\n");
  delay(3000);
  Serial.println("Inizializzato, in attesa di SMS");
  pinMode(CASA, OUTPUT);
  if(EEPROM.read(0)==0){
    digitalWrite(CASA, LOW);
  }
  else if(EEPROM.read(0)==1){
    digitalWrite(CASA, HIGH);
  }
  else{
    digitalWrite(CASA, HIGH);
  }
  //digitalWrite(CASA, HIGH);
}
char currentLine[500] = "";
int currentLineIndex = 0;
float temp;

bool nextIsMessage = false;
bool msgOn = false;

/*CODICE DEL PROGRMMA CHE VIENE RIPETUTO FINO ALL'ESECUZIONE DEL PROGRAMMA (LOOP)*/

void loop() {
  if (SIM800L.available()) {
    lastCharRead = SIM800L.read();
    
    if (lastCharRead == '\r' || lastCharRead == '\n') {
      lastLine = String(currentLine);

      if (((String)currentLine).startsWith("\"+39", 6) && ((String)currentLine).endsWith("\"")) {
        for (int i = 0; i < 10; i++) {
          numero[i] = (char)currentLine[i + 10];
        }
        numero[10] = '\0';
        //Serial.println(numero);
      }
      Serial.println(lastLine);

      if (lastLine.startsWith("+CMT:")) {
        nextIsMessage = true;
      } else if (lastLine.length() > 0) {
        if (nextIsMessage) {
          if (lastLine.startsWith("CASA ")) {
            if (lastLine.endsWith("ON")) {
              /*--------------ACCENSIONE CALDAIA------------*/
              temp = getVoltage(PTEMP);
              temp = getTemp(temp);
              digitalWrite(CASA, LOW);
              msgOn = true;
              EEPROM.write(0, 0);
              delay(1000);
              SIM800L.write("AT+CMGS=\"");
              SIM800L.write(numero);
              SIM800L.write("\"\r\n");
              delay(1000);
              SIM800L.write("HO ACCESO LA CALDAIA");
              SIM800L.write("LA TEMPERATURA IN CASA E' DI: ");
              SIM800L.print(temp);
              delay(1000);
              SIM800L.write((char)26);
              delay(1000);
              Serial.println("ACCESO");
            } else if (lastLine.endsWith("OFF")) {
              /*--------------SPEGNIMENTO CALDAIA------------*/
              /*COSA FARE IN CASO DI SPEGNIMENTO DELLA CALDAIA*/
              temp = getVoltage(PTEMP);
              temp = getTemp(temp);
              digitalWrite(CASA, HIGH);
              msgOn = false;
              EEPROM.write(0,1);
              delay(1000);
              SIM800L.write("AT+CMGS=\"");
              SIM800L.write(numero);
              SIM800L.write("\"\r\n");
              delay(1000);
              SIM800L.write("HO SPENTO LA CALDAIA");
              SIM800L.write("LA TEMPERATURA IN CASA E' DI: ");
              SIM800L.print(temp);
              delay(1000);
              SIM800L.write((char)26);
              delay(1000);
              Serial.println("SPENTO");
            } else if (lastLine.endsWith("TEMP")) {
              /*--------------INVIO TEMPERATURA------------*/
              temp = getVoltage(PTEMP);
              temp = getTemp(temp);
              Serial.print("temp=");
              Serial.print(temp);
              delay(1000);
              SIM800L.write("AT+CMGS=\"");
              SIM800L.write(numero);
              SIM800L.write("\"\r\n");
              delay(1000);
              SIM800L.write("LA TEMPERATURA IN CASA E' DI: ");
              SIM800L.print(temp);
              delay(1000);
              if(EEPROM.read(0)==0){
                  /*Carldaia accesa*/
                  delay(1000);
                  SIM800L.write("\nLa caldaia al momento risulta ACCESA");
                  delay(1000);
                  SIM800L.write((char)26);
                  delay(1000);
              }else if(EEPROM.read(0)==1){
                  /*Caldaia spenta*/
                  delay(1000);
                  SIM800L.write("\nLa caldaia al momento risulta SPENTA");
                  delay(1000);
                  SIM800L.write((char)26);
                  delay(1000);
              }
              Serial.println("INVIO TEMPERATURA");
            } else {
              Serial.println("Per l'opzione CASA scegliere tra ON, OFF o TEMP");
              delay(1000);
              SIM800L.write("AT+CMGS=\"");
              SIM800L.write(numero);
              SIM800L.write("\"\r\n");
              delay(1000);
              SIM800L.write("Il comando inviato è errato, verificare se il codice inserito è corretto.\n");
              SIM800L.write("Se il codice inserito risulta corretto contattare il Manutentore.");
              delay(1000);
              SIM800L.write((char)26);
              delay(1000);
              Serial.println("INVIO ERRORE 1");
            }
          }else if(lastLine.startsWith("ADMIN ")){
            if (lastLine.endsWith("STATUS")) {
              if(EEPROM.read(0)==0){
                /*Carldaia accesa*/
                delay(1000);
                SIM800L.write("AT+CMGS=\"");
                SIM800L.write(numero);
                SIM800L.write("\"\r\n");
                delay(1000);
                SIM800L.write("La caldaia al momento risulta ACCESA");
                delay(1000);
                SIM800L.write((char)26);
                delay(1000);
              }else if(EEPROM.read(0)==1){
                /*Caldaia spenta*/
                delay(1000);
                SIM800L.write("AT+CMGS=\"");
                SIM800L.write(numero);
                SIM800L.write("\"\r\n");
                delay(1000);
                SIM800L.write("La caldaia al momento risulta SPENTA");
                delay(1000);
                SIM800L.write((char)26);
                delay(1000);
              }
            }
          }else{
            Serial.println("Per l'opzione CASA scegliere tra ON, OFF o TEMP");
            delay(1000);
            SIM800L.write("AT+CMGS=\"");
            SIM800L.write(numero);
            SIM800L.write("\"\r\n");
            delay(1000);
            SIM800L.write("Il comando inviato è errato, verificare se il codice inserito è corretto.\n");
            SIM800L.write("Se il codice inserito risulta corretto contattare il Manutentore.");
            delay(1000);
            SIM800L.write((char)26);
            delay(1000);
            Serial.println("INVIO ERRORE 2");
          }

          nextIsMessage = false;
          SIM800L.println("AT+CMGD=1,4\r\n");
          delay(500);
          Serial.println("Messaggio cancellato!");
          lastLine="";
        }

      }

      for ( int i = 0; i < sizeof(currentLine); ++i ) {
        currentLine[i] = (char)0;
      }
      currentLineIndex = 0;
    } else {
      currentLine[currentLineIndex++] = lastCharRead;
    }
  }


  //if(Serial.available()){
  //  SIM800L.write(Serial.read());
  //}
}

float getTemp(float v){
  v = (v - 0.5) * 100;
  return v;
}

float getVoltage(int t) {
  return (analogRead(t) * .004882814);
}
