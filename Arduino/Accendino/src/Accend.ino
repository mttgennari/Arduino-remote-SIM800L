#include <SoftwareSerial.h>
#include <EEPROM.h>
#include "datatype.h"
//Definiamo una SoftwareSerial con i pin 8 ed 7 che devono essere collegati rispettivamente al TX ed RX del SIM800L   

float getVoltage(int);
data stato;

/*INIZIALIZZAZIONE DEL PROGRAMMA, CODICE ESEGUITO ALL'AVVIO*/

void setup() {
  //Inizializziamo la seriale dell'arduino verso il computer (fisica)
  Serial.begin(57600);
  while (!Serial);

  Serial.println("Serial ok!");

}

/*CODICE DEL PROGRMMA CHE VIENE RIPETUTO FINO ALL'ESECUZIONE DEL PROGRAMMA (LOOP)*/

void loop() {

// if stato_caldaia_on_server = acceso
//    caldaia = on
//    pubblica_nuovo_stato_caldaia_su server()
// endif

// acquire_temp(stato)

// publishdata(data)

}

void publishdata(){

}

float getTemp(float v){
  v = (v - 0.5) * 100;
  return v;
}

float getVoltage(int t) {
  return (analogRead(t) * .004882814);
}

