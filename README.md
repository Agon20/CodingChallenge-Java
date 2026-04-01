# Versicherungsprämien-Rechner

Anwendung zur Berechnung von Kfz-Versicherungsprämien basierend auf Kilometerleistung, Fahrzeugtyp und Region der Zulassung.

## Inhaltsverzeichnis

- [Architektur](#architektur)
- [Projektstruktur](#projektstruktur)
- [Technologie-Stack](#technologie-stack)
- [Prämienberechnung](#prämienberechnung)
- [Anwendung starten](#anwendung-starten)
- [API-Endpunkte](#api-endpunkte)
- [Tests](#tests)
- [Mögliche Erweiterungen](#mögliche-erweiterungen)

## Architektur

Modularer Monolith mit zwei fachlichen Services innerhalb einer Spring Boot Anwendung.

```
┌─────────────────────┐              ┌─────────────────────┐
│  Antragsteller      │              │  Externer           │
│  (Browser)          │              │  Dienstleister      │
└─────────┬───────────┘              └──────────┬──────────┘
          │                                     │
          ▼                                     │
┌─────────────────────┐                         │
│  index.html         │                         │
│  (Web-UI)           │                         │
└─────────┬───────────┘                         │
          │                                     │
          ▼                                     ▼
┌─────────────────────┐              ┌─────────────────────┐
│ApplicationController│              │CalculationController│
│POST /api/applications│             │POST /api/calculation│
└─────────┬───────────┘              └──────────┬──────────┘
          │                                     │
          ▼                                     │
┌─────────────────────┐                         │
│ ApplicationService  │                         │
│ Orchestrierung +    │                         │
│ Speicherung         │                         │
└───┬─────────┬───────┘                         │
    │         │                                 │
    │         └──────────────┐                  │
    │                        ▼                  ▼
    │              ┌────────────────────────────┐
    │              │ CalculationService         │
    │              │ Berechnung (zustandslos)   │
    │              └─────────────┬──────────────┘
    │                            │
    ▼                            ▼
┌──────────┐          ┌──────────────────┐
│PostgreSQL│          │PostcodeLoader    │
│          │          │(CSV im Speicher) │
└──────────┘          └──────────────────┘
```

**ApplicationService** – Nimmt Anträge entgegen, delegiert die Berechnung an den CalculationService, persistiert Eingaben und Ergebnis in PostgreSQL.

**CalculationService** – Berechnet die Prämie: `Kilometer-Faktor × Fahrzeugtyp-Faktor × Region-Faktor`. Zustandslos, keine Datenbankanbindung.

## Projektstruktur

```
src/main/java/com/example/codingchallengejava/
├── application/
│   ├── controller/      ApplicationController
│   ├── service/         ApplicationService
│   ├── dtos/            ApplicationRequest, ApplicationResponse
│   ├── entity/          InsuranceApplication (JPA Entity)
│   └── repository/      ApplicationRepository
├── calculation/
│   ├── controller/      CalculationController
│   ├── service/         CalculationService
│   ├── dtos/            CalculationRequest, CalculationResponse
│   ├── KilometerFactor, RegionFactor, VehicleTypeFactor
│   └── PostcodeLoader
└── exception/
    ├── GlobalExceptionHandler
    └── ErrorResponse
```

## Technologie-Stack

| Technologie | Verwendung |
|-------------|------------|
| Java 21 | Programmiersprache |
| Spring Boot 3.4 | Framework |
| PostgreSQL 16 | Datenbank |
| Spring Data JPA | Datenbankzugriff |
| JUnit 5 + Mockito | Tests |
| Swagger / OpenAPI | API-Dokumentation |
| Docker Compose | PostgreSQL-Container |

## Prämienberechnung

Formel: `Kilometerleistung-Faktor × Fahrzeugtyp-Faktor × Region-Faktor`

### Kilometerleistung-Faktoren

| Kilometerleistung | Faktor |
|-------------------|--------|
| 0 – 5.000 km | 0.5 |
| 5.001 – 10.000 km | 1.0 |
| 10.001 – 20.000 km | 1.5 |
| ab 20.001 km | 2.0 |

### Fahrzeugtyp-Faktoren

| Fahrzeugtyp | Wert | Faktor |
|-------------|------|--------|
| PKW | `PKW` | 1.0 |
| Motorrad | `MOTORRAD` | 1.3 |
| LKW | `LKW` | 1.8 |
| Elektroauto | `ELEKTROAUTO` | 0.9 |

### Region-Faktoren (Bundesland)

| Bundesland | Faktor |
|------------|--------|
| Berlin | 1.5 |
| Hamburg | 1.4 |
| Nordrhein-Westfalen | 1.3 |
| Bremen | 1.3 |
| Bayern | 1.2 |
| Baden-Württemberg | 1.1 |
| Hessen | 1.1 |
| Niedersachsen | 1.0 |
| Rheinland-Pfalz | 1.0 |
| Saarland | 1.0 |
| Sachsen | 1.0 |
| Schleswig-Holstein | 1.0 |
| Brandenburg | 0.9 |
| Sachsen-Anhalt | 0.9 |
| Thüringen | 0.9 |
| Mecklenburg-Vorpommern | 0.8 |

Die regionale Zuordnung erfolgt über die Postleitzahl. Der PostcodeLoader liest die CSV beim Start ein und mappt jede Postleitzahl auf das zugehörige Bundesland.

## Anwendung starten

### Voraussetzungen

- Java 21
- Docker (für PostgreSQL oder für die ganze Anwendung)

### Variante 1: PostgreSQL via Docker, App lokal

```bash
# PostgreSQL starten
docker-compose up -d postgres

# App starten
./mvnw spring-boot:run
```

### Variante 2: Alles via Docker

```bash
docker-compose up --build
```

### Aufrufen

| Beschreibung | URL |
|-------------|-----|
| Web-Oberfläche | http://localhost:8080 |
| API-Dokumentation | http://localhost:8080/swagger-ui/index.html |

### API testen (Drittanbieter-Endpunkt)

```bash
curl -X POST http://localhost:8080/api/calculation -H "Content-Type: application/json" -d "{\"postalCode\":\"10115\",\"annualMileage\":15000,\"vehicleTypeFactor\":\"PKW\"}"
```

## API-Endpunkte

### Antrag erstellen (mit Persistierung)

`POST /api/applications`

Request:
```json
{
  "postalCode": "10115",
  "annualMileage": 15000,
  "vehicleTypeFactor": "PKW"
}
```

Response:
```json
{
  "id": 1,
  "premium": 1.5,
  "createdDate": "2026-03-31T15:00:00"
}
```

### Prämie berechnen (ohne Persistierung)

`POST /api/calculation`

Request:
```json
{
  "postalCode": "10115",
  "annualMileage": 15000,
  "vehicleTypeFactor": "PKW"
}
```

Response:
```json
{
  "premium": 1.5
}
```

## Tests

```bash
./mvnw test
```

### Testkonzept

**Unit-Tests** testen die Geschäftslogik isoliert mit Mockito. Abhängigkeiten wie PostcodeLoader und Repository werden gemockt, sodass die Tests ohne Datenbank und ohne CSV-Datei laufen.

**Integration-Tests** testen die HTTP-Schicht mit MockMvc. Ein JSON-Request wird an den Controller geschickt und Status-Code sowie Response-Body geprüft.

| Testklasse | Typ | Was wird getestet |
|------------|-----|-------------------|
| AnnualMileageFactorTest | Unit | Grenzwerte der Kilometer-Stufen |
| RegionFactorTest | Unit | Alle 16 Bundesländer + Fehlerfall |
| CalculationServiceTest | Unit | Prämienberechnung mit verschiedenen Kombinationen |
| ApplicationServiceTest | Unit | Orchestrierung, Entity-Befüllung, Fehlerweiterleitung |
| CalculationControllerTest | Integration | HTTP-Endpunkt, JSON-Verarbeitung, Fehlerbehandlung |

## Mögliche Erweiterungen

- Authentifizierung der Drittanbieter-API (z.B. API-Keys, OAuth2)
- Fahrzeugtyp-Faktoren in Konfiguration oder DB-Tabelle auslagern
- Extraktion der Services in eigenständige Anwendungen bei steigenden Skalierungsanforderungen
- End-to-End-Tests mit Testcontainers gegen eine echte PostgreSQL-Instanz