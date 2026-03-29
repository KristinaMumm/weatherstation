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
    GET "/stations/list",         -- kõikide asukohtade pärimine
    GET "/stations/{id}/weather", -- etteantud asukoha ilmaandmed
    "/docs",                      -- API dokumentatsioon

Autentimine on lisatud mõttega, et igaüks ei saaks muuta rakenduse andmeid.

### Funktsionaalsuste kirjeldus

Rakendus võimaldab hallata asukohti (CRUD) ning pärida nende ilmaandmeid.


#### Mõned näidised:

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
See vähendaks päringute arvu välise API vastu ning parandaks jõudlust.

Logimise lisamine
