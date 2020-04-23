SELECT * FROM POCZTY;

SELECT * FROM ADRESY
ORDER BY NR_ADRESU;

-- Data za³o¿enia firmy/biura Happy Boat
SELECT DATA_ZALOZENIA FROM BIURA;

SELECT * FROM PORTY;

-- Porty firmy/biura Happy Boat
SELECT * FROM PORTY
WHERE NR_BIURA = 1;

-- Data za³o¿enia portów firmy/biura Happy Boat
SELECT DATA_ZALOZENIA FROM PORTY
WHERE NR_BIURA = 1;

-------- ZAPYTANIA O PRACOWNIKOW --------

-- Mê¿czyŸni pracuj¹cy w firmie/biurze Happy Boat
SELECT * FROM PRACOWNICY
WHERE PLEC = 'M' AND NR_BIURA = '1';

-- Ich liczba
SELECT COUNT(*) FROM PRACOWNICY
WHERE PLEC = 'M' AND NR_BIURA = '1';

-- Kobiety pracuj¹ce w firmie/biurze Happy Boat
SELECT * FROM PRACOWNICY
WHERE PLEC = 'K' AND NR_BIURA = '1';

-- Ich liczba
SELECT COUNT(*) FROM PRACOWNICY
WHERE PLEC = 'M' AND NR_BIURA = '1';

-- Pracownicy powy¿ej 25 roku ¿ycia
SELECT * FROM PRACOWNICY
WHERE DATA_URODZENIA > '95/04/20';

-- Utworzenie nowej, testowej tabeli --
CREATE TABLE PRAC_STAN (IMIE, NAZWISKO, STANOWISKO)
AS SELECT IMIE, NAZWISKO, STANOWISKO FROM PRACOWNICY;

SELECT * FROM PRAC_STAN;

DROP TABLE PRAC_STAN;
----------------------------------------

-- Modyfikacja pracownika
UPDATE PRACOWNICY SET WYNAGRODZENIE='2100' 
WHERE PESEL='920721001139';

-- Najlepiej zarabiaj¹cy pracownik
SELECT * FROM (SELECT * FROM PRACOWNICY 
ORDER BY WYNAGRODZENIE DESC) 
WHERE ROWNUM = 1 AND WYNAGRODZENIE IS NOT NULL;

-- Przedstawienie wyników zarobkowych
SELECT 'Pracownik '||nazwisko||' zarabia '||wynagrodzenie FROM pracownicy
where wynagrodzenie is not null;
-- lub
SELECT nazwisko, wynagrodzenie AS placa_podstawowa, wynagrodzenie*12 AS "roczna p³aca", wynagrodzenie/20 AS dniowka FROM pracownicy
where wynagrodzenie is not null;

-- Pracownicy z pocztana gmailu
SELECT * FROM PRACOWNICY
WHERE EMAIL LIKE '%gmail%'; -- case sensitive!

-- Takie tam
/*SELECT P1.IMIE, P2.NAZWISKO FROM PRACOWNICY P1
CROSS JOIN PRACOWNICY P2;*/

-- Adresy pracowników
SELECT IMIE, NAZWISKO, MIASTO, ULICA, NR_LOKALU FROM PRACOWNICY P
JOIN ADRESY A ON P.NR_ADRESU = A.NR_ADRESU;

-- Adresy pracowników
SELECT * FROM PRACOWNICY
NATURAL JOIN ADRESY;

SELECT * FROM ADRESY NATURAL JOIN POCZTY;

SELECT * FROM ADRESY A RIGHT OUTER JOIN POCZTY P ON A.NR_POCZTY = P.NR_POCZTY;
SELECT * FROM ADRESY A LEFT OUTER JOIN POCZTY P ON A.NR_POCZTY = P.NR_POCZTY;

SELECT * FROM ADRESY A, POCZTY P WHERE A.NR_POCZTY(+) = P.NR_POCZTY;
SELECT * FROM ADRESY A, POCZTY P WHERE A.NR_POCZTY = P.NR_POCZTY(+);


