CREATE TABLE Clients (
  client_id serial PRIMARY KEY,
  client_name VARCHAR(255),
  contact_person VARCHAR(255),
  address text,
  phone VARCHAR(15),
  email VARCHAR(255)
);

CREATE TABLE Employees (
  employee_id serial PRIMARY KEY,
  employee_name VARCHAR(255),
  address text,
  phone VARCHAR(15),
  email VARCHAR(255),
  function_ VARCHAR(255)
);

CREATE TABLE Services (
  service_id serial PRIMARY KEY,
  service_name VARCHAR(255),
  cost numeric
);

CREATE TABLE ServiceHistory (
  history_id serial PRIMARY KEY,
  client_id INT,
  employee_id INT,
  service_id INT,
  begin_ DATE,
  end_ DATE,
  FOREIGN KEY (client_id) REFERENCES Clients(client_id),
  FOREIGN KEY (employee_id) REFERENCES Employees(employee_id),
  FOREIGN KEY (service_id) REFERENCES Services(service_id)
);