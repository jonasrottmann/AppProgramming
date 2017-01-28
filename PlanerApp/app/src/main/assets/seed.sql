CREATE TABLE "course" (
  "_id" integer PRIMARY KEY AUTOINCREMENT NOT NULL,
  "name" text NOT NULL,
  "teacher" text,
  "room" text,
  "timeslot" integer NOT NULL,
  "weekday" integer NOT NULL,
  "category" integer,
  "starred" integer NOT NULL DEFAULT(0),
  "icon" integer NOT NULL DEFAULT(-1)
);
INSERT INTO "course" VALUES(1,'Asien-Versammlung',NULL,'Russland',0,0,7,0,0);
INSERT INTO "course" VALUES(2,'Chemie III','Jan','Mongolei',1,0,6,0,0);
INSERT INTO "course" VALUES(3,'Deutsch Werkstatt','Doreen, Dörte','Japan',1,0,5,0,1);
INSERT INTO "course" VALUES(4,'Garten','Alex','Garten',1,0,1,0,0);
INSERT INTO "course" VALUES(5,'Impro-Theater','Julia','Russland',1,0,1,0,0);
INSERT INTO "course" VALUES(6,'Kochen','Pamela','Hawaï Küche',1,0,1,0,0);
INSERT INTO "course" VALUES(7,'Lesen, schreiben, rechnen','Hagen, Felix','USA',1,0,5,0,2);
INSERT INTO "course" VALUES(8,' Was interessiert mich','Sebastian','Panama',1,0,0,0,0);
INSERT INTO "course" VALUES(9,'Chemie III','Jan','Mongolei',2,0,6,0,0);
INSERT INTO "course" VALUES(10,'Deutsch Werkstatt','Doreen, Dörte','Japan',2,0,5,0,1);
INSERT INTO "course" VALUES(11,'Garten','Alex','Garten',2,0,1,0,0);
INSERT INTO "course" VALUES(12,'Kochen','Pamela','Hawaï Küche',2,0,1,0,0);
INSERT INTO "course" VALUES(13,'Kritisches Denken','Henrik','Bhutan',2,0,0,0,0);
INSERT INTO "course" VALUES(14,'Mathe Werkstatt','Auré ','Tibet',2,0,0,0,1);
INSERT INTO "course" VALUES(15,'Physik I','Martin','Indien',2,0,6,0,0);
INSERT INTO "course" VALUES(16,'Schach','Olaf','Mexico',2,0,0,0,0);
INSERT INTO "course" VALUES(17,'Schmuck','Grit-Ute','China',2,0,1,0,0);
INSERT INTO "course" VALUES(18,'Sport bis 3. Klasse','Hagen',NULL,2,0,4,0,0);
INSERT INTO "course" VALUES(19,'Flexizeit',NULL,NULL,3,0,7,0,0);
INSERT INTO "course" VALUES(20,'Kochen','Pamela','Hawaï Küche',3,0,1,0,0);
INSERT INTO "course" VALUES(21,'Lernbüro',NULL,'Tibet',3,0,7,0,0);
INSERT INTO "course" VALUES(22,'Schach','Olaf','Mexico',3,0,0,0,0);
INSERT INTO "course" VALUES(23,'Schmuck','Grit-Ute','China',3,0,1,0,0);
INSERT INTO "course" VALUES(24,'Sport ab 4. Klasse','Hagen',NULL,3,0,4,0,0);
INSERT INTO "course" VALUES(25,'VZK','Sebastian, Felix','Malaysia',3,0,7,0,0);
INSERT INTO "course" VALUES(26,'Chemie II','Jan','Mongolei',5,0,6,0,0);
INSERT INTO "course" VALUES(27,'Englisch - Grundschule','Daniela','Panama',5,0,5,0,0);
INSERT INTO "course" VALUES(28,'Grundschul-Teamtreff',NULL,NULL,5,0,7,0,0);
INSERT INTO "course" VALUES(29,'Japanisch','Atsuko','China',5,0,5,0,0);
INSERT INTO "course" VALUES(30,'Nähen','Dörte','Nähzimmer',5,0,1,0,0);
INSERT INTO "course" VALUES(31,'Englisch - Grundschule','Daniela','Panama',6,0,5,0,0);
INSERT INTO "course" VALUES(32,'Gemeinschaftskunde','Paula','Tibet',6,0,3,0,0);
INSERT INTO "course" VALUES(33,'Japanisch','Atsuko','China',6,0,5,0,0);
INSERT INTO "course" VALUES(34,'Gemeinschaftskunde','Paula','Tibet',7,0,3,0,0);