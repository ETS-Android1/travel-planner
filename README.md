# Android Travel Planner Application
Rozbudowana aplikacja ułatwiająca planowanie podróży grupie osób przeznaczona na urządzania mobilne z systemem Android.
Posiada główne funkcjonalności takie jak czat, forum, galeria zdjęć, plan podróży, ToDo list and settlements.

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Screenshots](#screenshots)
* [Setup](#setup)
* [Project Status](#project-status)
* [Room for Improvement](#room-for-improvement)
* [Contact](#contact)


## General Information
Projekt był rozwijany w 4osobowym zespole w ramach pracy na uczelni. Aplikacja stara się rozwiązać problem skutecznej
organizacji wyjazdów turystycznych. W ramach rowiązania problemu powstała apliakcja łącząca uczestników wyjazdu i ułatwiająca organizację.
Aplikacja ma ułatwiać kontakt pomiędzy osobami biorącymi udział w wyjeździe, umożliwić ustalenie planuwycieczki oraz planu przygotowań
i rozdzielić obowiązki między uczestników. Ponadto w aplikacji użytkownicy mogą na bieżąco rejestrować swoje wydatki,
co ułatwi rozliczenie kosztów wycieczki, jak także dzielić się z innymi swoimi zdjęciami z wyjazdu. 


## Technologies Used
- Material Design 1.3.0
- Kotlin 1.5.0
- Firebase Messaging Service 20.0.0
- Google Maps Services 18.0.0
- Navigation Graph
- ViewBinding
- MySQL database with JDBC 5.1.49


## Features
List the ready features here:
* czat i forum
* galeria zdjęć
* tworzenie planu wycieczki
* mapa z trasą wycieczki
* TODO list and TO TAKE list
* prowadzenie rozliczeń
* dodawanie rozliczeń
* zapraszanie innych użytkowników do wycieczki
* założenie konta
* powiadomienia
* udostępnianie lokalizacji innym uczestnikom
 

## Screenshots
Application screen review
### Główny ekran aplikacji
<p align="center">
	<img scr="./screenshots/app_main_screen.png" width="250">
</p>

### Ekrany wycieczki

<p align="center">
	<img scr="./screenshots/trip_main_screen.png" width="250">
	<img scr="./screenshots/todo_screen.png" width="250">
	<img scr="./screenshots/trip_plan_screen.png" width="250">
	<img scr="./screenshots/map_screen.png" width="250">
	<img scr="./screenshots/settlements_screen.png" width="250">
</p>

## Setup
1. Download the samples by cloning this repository
2. In the welcome screen of Android Studio, select "Open an Existing project"
3. Select one of the sample directories from this repository

Alternatively, use the `gradlew build` command to build the project directly

Konieczne jest połączenie projektu z bazą danych oraz z usługami Google Maps, aby możliwe było korzystanie w pełni z apliakcji.
Aby aplikacja mogła korzystać z bazy danych, pliku [BaseConnection](./ZPI/app/src/main/java/com/example/zpi/data_handling/BaseConnection.java) należy ustwić _connection URL_,
_username_ and _password_ waszej bazy danych. Kolejnym krokiem jest utworzenie konta w Google Cloud Platform według instrukcji
na stronie [cloud.google.com](https://cloud.google.com/apigee/docs/hybrid/v1.3/precog-gcpaccount) i stworzenie projektu.
W projekcie należyodszukać i aktywować następujące interfejsy API _Directions API_, _Maps SDK for Android_ and _Places API_.
Gdy interfejsy są aktywne w projekcie w pliku [strings](./ZPI/app/src/main/res/values/strings.xml) należy ustawić klucz API wygenerowany dla naszego projektu.

## Database
Hostowana na https://www.db4free.net/  
Server: db4free.net  
DB Name: zpi_test_db  
Username: zpi_test_user  
Password: zpi_test_password  
Port number: 3306    

Zapisywanie do bazy bedzie robione z użyciem DAO, które są w folderze repositories


## Project Status
Project is: _complete_


## Room for Improvement
To do:
* dodawanie kategorii wydatków i na ich podstawie tworzenie wykresów
* obsługa urządzeń bez funkcji lokalizacji


Do poprawy:
* zarządzanie uczestnikami wycieczki

## Contact
Created by [@HKonstanty](https://github.com/HKonstanty/HKonstanty) - feel free to contact me!