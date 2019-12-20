CREATE TABLE students(
student_id VARCHAR(8) NOT NULL,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(20) NOT NULL,
faculty VARCHAR(5) NOT NULL,
contact_number VARCHAR(13),
email VARCHAR(30),
password_code VARCHAR(15)NOT NULL,
PRIMARY KEY(student_id)
);
CREATE TABLE authors(
author_id INT GENERATED ALWAYS AS IDENTITY,
first_name VARCHAR(20),
last_name VARCHAR(20),
PRIMARY KEY(author_id)
);
CREATE TABLE book_group(
isbn INT NOT NULL,
category VARCHAR(30),
descrip VARCHAR(305),
title VARCHAR(30),
quantity INT NOT NULL DEFAULT 0,
available INT NOT NULL DEFAULT 0,
PRIMARY KEY(isbn)
);
CREATE TABLE author_books(
author_id INT NOT NULL,
isbn INT NOT NULL, 
PRIMARY KEY(author_id, isbn),
FOREIGN KEY(author_id) REFERENCES authors(author_id) ON DELETE CASCADE,
FOREIGN KEY(isbn) REFERENCES book_group(isbn) ON DELETE CASCADE
);
CREATE TABLE book(
book_id INT GENERATED ALWAYS AS IDENTITY,
isbn INT NOT NULL,
PRIMARY KEY(book_id),
FOREIGN KEY(isbn) REFERENCES book_group(isbn) ON DELETE CASCADE
);
CREATE TABLE lost_books(
book_id INT NOT NULL,
isbn INT NOT NULL,
student_id VARCHAR(8) NOT NULL,
lost_date DATE,
PRIMARY KEY(book_id),
FOREIGN KEY(book_id) REFERENCES book(book_id) ON DELETE CASCADE,
FOREIGN KEY(student_id) REFERENCES students(student_id) ON DELETE CASCADE
);
CREATE TABLE fined_students(
student_id VARCHAR(8) NOT NULL,
book_id INT,
due_date DATE,
charge_amount INT,
PRIMARY KEY(student_id, book_id),
FOREIGN KEY(student_id) REFERENCES students(student_id) ON DELETE CASCADE,
FOREIGN KEY(book_id) REFERENCES book(book_id) ON DELETE CASCADE
);
CREATE TABLE librarian(
librarian_id VARCHAR(8) NOT NULL,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(20),
contact_number VARCHAR(13),
email VARCHAR(30),
password_code VARCHAR(15) NOT NULL,
PRIMARY KEY(librarian_id)
);
CREATE TABLE admin_table(
admin_id VARCHAR(8) NOT NULL,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(20),
contact_number VARCHAR(13),
email VARCHAR(30),
password_code VARCHAR(15) NOT NULL,
PRIMARY KEY(admin_id)
);
CREATE TABLE blocked_students(
student_id VARCHAR(8) NOT NULL,
reason VARCHAR(30) NOT NULL,
PRIMARY KEY(student_id),
FOREIGN KEY(student_id) REFERENCES students(student_id) ON DELETE CASCADE
);
CREATE TABLE waiting_list(
student_id VARCHAR(8) NOT NULL,
isbn INT NOT NULL,
reservation_date DATE,
waiting_until DATE,
PRIMARY KEY(student_id, isbn),
FOREIGN KEY(student_id) REFERENCES students(student_id) ON DELETE CASCADE,
FOREIGN KEY(isbn) REFERENCES book_group(isbn) ON DELETE CASCADE
);
CREATE TABLE ordered_books(
isbn INT NOT NULL,
student_id VARCHAR(8) NOT NULL,
issued_date DATE,
due_date DATE,
PRIMARY KEY(isbn, student_id),
FOREIGN KEY(isbn) REFERENCES book_group(isbn) ON DELETE CASCADE,
FOREIGN KEY(student_id) REFERENCES students(student_id) ON DELETE CASCADE
);
INSERT INTO students VALUES
('U1810001', 'ABDULAZIZ', 'ABED', 'CIE', '+998998990000', 'a.abed@student.inha.uz', 'abd0000'),
('U1810002', 'ALEKSANDRA', 'ALEKSANDRA', 'CIE', '+998998990001', 'a.alexandra@student.inha.uz', 'ale0001'),
('U1810004', 'FARRUKH', 'ODILOV', 'CIE', '+998998990002', 'f.odilov@student.inha.uz', 'far0002'),
('U1810005', 'AKBARBEK', 'JUMANAZAROV', 'CIE', '+998998990003', 'a.jumanazarov@student.inha.uz', 'akb0003'),
('U1810006', 'ABDUVOHID', 'ISROILOV', 'CIE', '+998998990004', 'a.isroilov@student.inha.uz', 'abd0004'),
('U1810007', 'AL-XAFIS', 'MUZRAFKULOV', 'CIE', '+998998990005', 'a.muzrafkulov@student.inha.uz', 'al-0005'),
('U1810008', 'FAYZIDIN', 'SAYLIEV', 'CIE', '+998998990006', 'f.sayliev@student.inha.uz', 'fay0006'),
('U1810009', 'KHUMOYUN', 'KHUJAMOV', 'CIE', '+998998990007', 'k.khujamov@student.inha.uz', 'khu0007'),
('U1810010', 'AKHROR', 'KHAMRAEV', 'CIE', '+998998990008', 'a.@student.inha.uz', 'akh0018'),
('U1810012', 'NURZIYO', 'ABDUKARIMOVA', 'CIE', '+998998990009', 'n.abdukarimova@student.inha.uz', 'nur0019'),
('U1810014', 'EKATERINA', 'SMIRNOVA', 'CIE', '+998998990010', 'e.smirnova@student.inha.uz', 'eka0010'),
('U1810015', 'SARVINOZ', 'ABDULLAEVA', 'CIE', '+998998990011', 's.abdullaeva@student.inha.uz', 'sar0011'),
('U1810016', 'AZIZJON', 'GOFIROV', 'CIE', '+998998990012', 'a.gofirov@student.inha.uz', 'azi0012'),
('U1810017', 'SARVAR', 'ARIFDJANOV', 'CIE', '+998998990013', 's.arifdjanov@student.inha.uz', 'sar0013'),
('U1810018', 'RISKIYA', 'ABROROVA', 'CIE', '+998998990014', 'r.abrorova@student.inha.uz', 'ris0014'),
('U1810019', 'ASILBEK', 'TILAVOV', 'CIE', '+998998990015', 'a.tilavov@student.inha.uz', 'asi0015'),
('U1810022', 'SARDOR', 'BAYRAMOV', 'CIE', '+998998990016', 's.bayramov@student.inha.uz', 'sar0026'),
('U1810023', 'OYATULLO', 'KORABOEV', 'CIE', '+998998990017', 'o.koraboev@student.inha.uz', 'oya0027'),
('U1810024', 'MADINABONU', 'ALISHEROVA', 'CIE', '+998998990018', 'm.alisherova@student.inha.uz', 'mad0028'),
('U1810025', 'SHUKHRAT', 'KHAMRAEV', 'CIE', '+998998990019', 's.khamraev@student.inha.uz', 'shu0029');

INSERT INTO librarian VALUES
('U16102', 'Ulugbek', 'Turaev', '+998908870502', 'u.turaev@mail.inha.uz', 'ult0502'),
('U16105', 'Shohjahon', 'Nurmatov', '+998901253334', 's.nurmatov@mail.inha.uz', 'shn3334'),
('U18012', 'Jasurbek', 'Jamolov', '+998933926654', 'j.jamolov@mail.inha.uz', 'jaj6654'),
('U17025', 'Madina', 'Botirova', '+998998088505', 'm.botirova@mail.inha.uz', 'mab8505'),
('U17043', 'Ziyoda', 'Murodova', '+998974611767', 'z.murodova@mail.inha.uz', 'zim1767');

INSERT INTO admin_table VALUES
('U1810171', 'Tukhtamurod', 'Isroilov', '+998901701111', 't.isroilov@student.mail.uz', 'webhero171'),
('U1810151', 'Mubina', 'Mardonova', '+998935318688', 'm.mardonova@student.inha.uz', 'anibum801'),
('U1810149', 'Sabokhat', 'Juraeva', '+998998077755', 's.juraeva@student.mail.uz', 'sabo0002');

CREATE TRIGGER insert_into_book
AFTER INSERT ON book
REFERENCING NEW AS new
FOR EACH ROW MODE DB2SQL
UPDATE book_group SET quantity=1+(SELECT quantity FROM book_group WHERE isbn=new.isbn), 
available=1+(SELECT available FROM book_group WHERE isbn=new.isbn) WHERE isbn=new.isbn;

CREATE TRIGGER delete_from_book
AFTER DELETE ON book
REFERENCING OLD AS old
FOR EACH ROW MODE DB2SQL
UPDATE book_group SET quantity=(SELECT quantity FROM book_group WHERE isbn=old.isbn)-1, 
available=(SELECT available FROM book_group WHERE isbn=old.isbn)-1 WHERE isbn=old.isbn;

CREATE TRIGGER insert_into_ordered_books
AFTER INSERT ON ordered_books
REFERENCING NEW AS new
FOR EACH ROW MODE DB2SQL
UPDATE book_group SET available=(SELECT available FROM book_group WHERE isbn=new.isbn)-1;

CREATE TRIGGER delete_from_ordered_books
AFTER DELETE ON ordered_books
REFERENCING OLD AS old
FOR EACH ROW MODE DB2SQL
UPDATE book_group SET available=(SELECT available FROM book_group WHERE isbn=old.isbn)+1;

CREATE TRIGGER insert_into_lost_books
AFTER INSERT ON lost_books
REFERENCING NEW AS new
FOR EACH ROW MODE DB2SQL
DELETE FROM book WHERE book_id=new.book_id;

CREATE TRIGGER delete_from_lost_books
AFTER DELETE ON lost_books
REFERENCING OLD AS old
FOR EACH ROW MODE DB2SQL
INSERT INTO book(isbn) VALUES(old.isbn);

CREATE TRIGGER insert_into_waiting_list
AFTER INSERT ON waiting_list
REFERENCING NEW AS new
FOR EACH ROW MODE DB2SQL
UPDATE waiting_list SET waiting_until=(SELECT Min(due_date) FROM ordered_books 
WHERE ordered_books.isbn=new.isbn)
WHERE student_id=new.student_id;
