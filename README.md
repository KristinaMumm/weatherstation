# Proovitöö: WeatherStation API

WeatherStation API on rakendus ilmaandmete pärimiseks eelnevalt salvestatud asukohtade kohta. Rakendus sisaldab ka
asukohtade haldamise funktsionaalsust.

## Tehnoloogiad
* Java 17
* Spring Boot 4
  * Spring Web (REST API)
  * Spring Data JPA
  * Spring Security
* Maven
* MySQL 8
* Flyway
* Springdoc OpenAPI ja Swagger UI
* Docker


## Rakenduse ülesseadmine

Rakenduse ülesseadmiseks on vajalik Docker.

Esmaseks rakenduse käivitamiseks (toimub rakenduse package ja konteinerite loomine):

    docker-compose up --build -d

Edaspidiseks käivituseks:

    docker-compose up -d

Rakenduse peatamiseks:

    docker-compose down

## Rakenduse kasutamine

Rakenduse käivitamisel käivitatakse andmebaas ja rakendus ise.
Andmebaas
*  kasutaja: `admin`
*  parool: `password`
*  port: `3306`

Rakendus
* kasutaja: `admin`
* parool: `password`
* port: `8080`

Need on vaikimisi väärtused, mida loetakse failist `.env`

Flyway migratsiooniga lisatakse tabel `station` koos mõne kirjega.

Rakenduse API endpoindid on kättesaadavad siin: http://localhost:8080/docs.

Järgnevad endpoindid on avalikud, ülejäänud nõuavad autentimist:

    GET "/health",                -- test endpoint veendumaks, et rakendus töötab 
    GET "/stations/{id}",         -- etteantud asukoha pärimine
    GET "/stations",              -- kõikide asukohtade pärimine
    GET "/stations/{id}/weather", -- etteantud asukoha ilmaandmed
    "/docs",                      -- API dokumentatsioon

Autentimine on lisatud mõttega, et igaüks ei saaks muuta rakenduse andmeid.

### Funktsionaalsuste kirjeldus

Rakendus võimaldab hallata asukohti (CRUD) ning pärida nende ilmaandmeid.


#### Mõned näidised: (rohkemate endpointide nägemiseks külasta API dokumentatsiooni)

Asukohtade pärimine

Päring
```bash
curl --location --request GET 'localhost:8080/stations'
```
Vastus
```json
{
  "data": [
    {
      "id": 1,
      "latitude": 58.365,
      "longitude": 26.687,
      "name": "Lõunakeskus, Tartu"
    },
    {
      "id": 2,
      "latitude": 41.4036,
      "longitude": 2.1744,
      "name": "Sagrada Familia, Barcelona"
    },
    {
      "id": 3,
      "latitude": 64.1425,
      "longitude": -21.9266,
      "name": "Hallgrimskirkja, Reykjavik"
    }
  ],
  "errors": [
  ]
}
```

Asukoha lisamine

Päring
```bash
curl --location --request POST 'localhost:8080/stations' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
--data '{
"name": "test station",
"latitude": 11,
"longitude": 11
}'
```
Vastus
```json
{
  "data": {
    "id": 4,
    "latitude": 11.0,
    "longitude": 11.0,
    "name": "test station"
  },
  "errors": [
  ]
}
```

Asukoha kustutamine

Päring
```bash
curl --location --request DELETE 'localhost:8080/stations/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
``` 

Asukoha ilmaandmed

Päring
```bash
curl --location --request GET 'localhost:8080/stations/1/weather'
```   
Vastus
```json
{
  "data": [
    {
      "stationId": 1,
      "temperature": 7.5,
      "windSpeed": 2.1,
      "precipitation": 0.0
    }
  ],
  "errors": []
}
```


## Edasiarendus
Kuna ilmaandmed ei muutu väga tihti, võiks need vahemällu salvestada.
See vähendaks päringute arvu välise API vastu(äkki isegi hoiaks niimoodi raha kokku, kui tegemist tasuliste päringutega) 
ning parandaks jõudlust.

Lisaks tuleb arvestada, et Open-Meteo ei pruugi tagastada andmeid täpselt etteantud koordinaatidele, vaid võib need 
ümardada või kasutada lähimat saadaval olevat punkti.
See tähendab, et tuleb kontrollida, mis juhtub olukorras, kus Open-Meteo API ümardab kahe või enama erineva asukoha koordinaadid samaks.
Kas teenus tagastab sellisel juhul ühe või mitu ilmaandmete kirjet sama ümardatud koordinaadi kohta?
See küsimus muutub oluliseks ainult juhul, kui peaks olema võimalik pärida mitme asukoha ilmaandmeid korraga.
Kuigi vastav loogika on koodis olemas, ei ole sellele hetkel eraldi endpointi lisatud.

Logimise lisamine

Leida parem java koodi formatter ja lisada projektile. Kasutasin hetkel `google-java-formatter`, aga mulle ei meeldi, kuidas see osati tegutseb.

## Testid

Lisatud on kolme tüüpi teste:

* Unit-testid `StationService` jaoks
* Integratsioonitestid `StationService` jaoks, et veenduda andmebaasi salvestamise korrektsuses
* Integratsioonitestid `StationManagerController` jaoks, et kontrollida, kas API päringutele tagastatakse õiged vastused

Teste tuleks kindlasti edasi arendada. Vaja on lisada uusi teste, et katta kõik funktsionaalsused ja äärejuhtumid.
Samuti oleks vajalik refaktoreerida olemasolevaid teste, viies korduv loogika abimeetoditesse. Praegusel kujul kipuvad testid sisaldama palju korduvat koodi, mis muudab nende haldamise keeruliseks – eriti olukorras, kus koodis tehakse muudatusi, mis mõjutavad mitut testi korraga.

Lisaks oleks mõistlik uurida, kas olemasolevad migratsioonid mõjutavad testide käivitamise kiirust. Hetkel on ainult üks migratsioon ning pole täielikult selge, kas ja kuidas see testide käigus rakendub.

## Arhitektuurilised otsused, milles veidi kahtlen

Hetkel on kõik API vastused nn `result envelope` sees. See tähendab, et iga vastus sisaldab vähemalt kahte välja: `data` ja `errors`.

Eelistan seda lähenemist, kuna see tagab ühtse struktuuri sõltumata HTTP staatuskoodist – alati on olemas samad väljad, kust andmeid lugeda.
Lisaks võimaldab see lisada ka õnnestunud päringute puhul hoiatusi või osalisi vigu. Näiteks massimporti korral võib osa kirjeid edukalt salvestuda ja osa ebaõnnestuda.

Teine kahtlus on seotud laius- ja pikkuskraadide valideerimisega. Kas seda peaks tegema nii controlleri kui ka andmebaasi tasemel?
Minu hinnangul on mõistlik kasutada mõlemat:

* Controlleri tasemel valideerimine tagab parema dokumentatsiooni ja varajase vea tagasiside kasutajale
* Andmebaasi tasemel valideerimine tagab, et sõltumata sellest, kust andmeid lisatakse, jäävad väärtused alati lubatud piiridesse
