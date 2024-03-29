-- filling of Clients
INSERT INTO Clients (client_name, contact_person, address, phone, email) VALUES
('Иванов Иван Иванович', 'Иванов Иван Иванович', '123 Main St', '123-456-7890', 'ivanov@example.com'),
('Петров Петр Петрович', 'Петров Петр Петрович', '456 Oak St', '987-654-3210', 'petrov@example.com'),
('Смирнова Елена Сергеевна', 'Смирнова Елена Сергеевна', '789 Pine St', '555-123-4567', 'smirnova@example.com'),
('Коваленко Александр Андреевич', 'Коваленко Александр Андреевич', '321 Elm St', '111-222-3333', 'kovalenko@example.com'),
('Михайлова Ольга Алексеевна', 'Михайлова Ольга Алексеевна', '567 Birch St', '999-888-7777', 'mikhailova@example.com'),
('ООО "Юридическая фирма Альфа"', 'Сидоров Игорь Петрович', '789 Maple St', '777-555-8888', 'alfa@example.com');

-- filling of Employees
INSERT INTO Employees (employee_name, address, phone, email, function_) VALUES
('Кузнецов Артем Валерьевич', '789 Hill St', '111-222-3333', 'kuznetsov@example.com', 'Юрист'),
('Калинина Екатерина Игоревна', '234 Valley St', '444-555-6666', 'kalinina@example.com', 'Помощник юриста'),
('Белякова Мария Дмитриевна', '876 Mountain St', '777-888-9999', 'belyakova@example.com', 'Паралигал'),
('Гусев Станислав Иванович', '543 Lake St', '222-333-4444', 'gusev@example.com', 'Адвокат'),
('Сорокина Анастасия Александровна', '987 River St', '666-777-8888', 'sorokina@example.com', 'Секретарь юриста');

-- filling of Services
INSERT INTO Services (service_name, cost) VALUES
('Legal Consultation', 150.00),
('Document Creation', 200.00),
('Contract Review', 180.00),
('Litigation Support', 250.00),
('Corporate Law Advice', 300.00);

-- filling of ServiceHistory
INSERT INTO ServiceHistory (client_id, employee_id, service_id, begin_, end_) VALUES
(1, NULL, 1, '2024-03-23', NULL),
(2, 2, 3, '2024-02-24', '2024-02-25'),
(3, 3, 2, '2024-02-25', '2024-02-25'),
(4, 4, 4, '2024-02-26', NULL),
(5, 5, 5, '2024-02-27', '2024-03-20');