package ru.msu.cmc.webprac;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class WebTests {

    private final String index = "Главная страница";
    private final String clients = "Клиенты";
    private final String clients_edit = "Редактирование Клиентов";
    private final String employees = "Служащие";
    private final String employees_edit = "Редактирование Служащих";
    private final String services = "Услуги";
    private final String services_edit = "Редактирование Услуг";
    private final String serviceHistory = "Оказанные услуги";
    private final String serviceHistory_edit = "Удаление Оказанных услуг";

    @Test
    void Index() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        assertEquals(index, driver.getTitle());
    }

    @Test
    void HeaderTest() {
        //index
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.get("http://localhost:8080/");
        WebElement rootButton = driver.findElement(By.id("rootLink"));
        rootButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(index, driver.getTitle());

        //clients
        WebElement clientsButton = driver.findElement(By.id("clientsListLink"));
        clientsButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(clients, driver.getTitle());

        //employees
        WebElement employeesButton = driver.findElement(By.id("employeesListLink"));
        employeesButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(employees, driver.getTitle());

        //services
        WebElement servicesButton = driver.findElement(By.id("servicesListLink"));
        servicesButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(services, driver.getTitle());

        //serviceHistory
        WebElement serviceHistoryButton = driver.findElement(By.id("serviceHistoryListLink"));
        serviceHistoryButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(serviceHistory, driver.getTitle());
    }


    //tests of clients Methods
    @Test
    void addClient_then_search_then_update_then_delete() {
        //add
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.get("http://localhost:8080/clients");
        assertEquals(clients, driver.getTitle());

        driver.findElement(By.id("client_Name_add")).sendKeys("Тест");
        driver.findElement(By.id("contact_person")).sendKeys("Тест");
        driver.findElement(By.id("address")).sendKeys("Тестовый адресс");
        driver.findElement(By.id("phone")).sendKeys("666-666-6666");
        driver.findElement(By.id("email")).sendKeys("test@test.com");
        driver.findElement(By.id("addClient")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(clients, driver.getTitle());

        //search
        driver.get("http://localhost:8080/clients");
        assertEquals(clients, driver.getTitle());

        driver.findElement(By.id("client_Name_search")).sendKeys("Тест");
        driver.findElement(By.id("contact_person_search")).sendKeys("Тест");
        driver.findElement(By.id("address_search")).sendKeys("Тестовый адресс");
        driver.findElement(By.id("phone_search")).sendKeys("666-666-6666");
        driver.findElement(By.id("email_search")).sendKeys("test@test.com");
        driver.findElement(By.id("searchClients")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(clients, driver.getTitle());
        WebElement clientInfo = driver.findElement(By.id("clientsList"));
        List<WebElement> cells = clientInfo.findElements(By.tagName("td"));

        assertEquals("Тест", cells.get(0).getText());
        assertEquals("Тест", cells.get(1).getText());
        assertEquals("Тестовый адресс", cells.get(2).getText());
        assertEquals("666-666-6666", cells.get(3).getText());
        assertEquals("test@test.com", cells.get(4).getText());
        assertEquals(clients, driver.getTitle());

        WebElement clientsButton = driver.findElement(By.id("clientsListLink"));
        clientsButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(clients, driver.getTitle());

        //edit
        driver.get("http://localhost:8080/clients");
        assertEquals(clients, driver.getTitle());

        driver.findElement(By.id("clients_edit")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(clients_edit, driver.getTitle());

        WebElement editList = driver.findElement(By.id("clientsEditList"));
        List<WebElement> rows = editList.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> elements = row.findElements(By.tagName("td"));

            for (WebElement cell : elements) {
                if (cell.getText().equals("Тестовый адресс")) {
                    cell.clear();
                    cell.sendKeys("Изменил адресс для теста");

                    WebElement saveButton = row.findElement(By.xpath(".//button[contains(text(), 'Готово!')]"));
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", saveButton);
                    break;
                }
            }
        }

        assertEquals(clients_edit, driver.getTitle());
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        driver.findElement(By.id("endEdit")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(clients, driver.getTitle());
        //test that we changed address of the test person
        WebElement clientInfo_new = driver.findElement(By.id("clientsList"));
        List<WebElement> rows_new = clientInfo_new.findElements(By.tagName("tr"));
        boolean flag = false;
        for (WebElement row : rows_new) {
            List<WebElement> rowElements = row.findElements(By.tagName("td"));

            for (WebElement cell : rowElements) {
                if (cell.getText().equals("Изменил адресс для теста")) {
                    flag = true;
                    break;
                }
            }
        }
        assertTrue(flag);

        //delete
        driver.get("http://localhost:8080/clients");
        assertEquals(clients, driver.getTitle());

        driver.findElement(By.id("clients_edit")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(clients_edit, driver.getTitle());

        WebElement ClientDelInfo = driver.findElement(By.id("clientsEditList"));
        List<WebElement> tr = ClientDelInfo.findElements(By.tagName("tr"));
        outerloop:
        for (WebElement row : tr) {
            List<WebElement> td = row.findElements(By.tagName("td"));

            for (WebElement cell : td) {
                if (cell.getText().equals("Тест")) {
                    WebElement deleteButton = row.findElement(By.xpath(".//button[contains(text(), 'Удалить из базы')]"));
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", deleteButton);
                    break outerloop;
                }
            }
        }

        //test that we deleted the test person
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(clients_edit, driver.getTitle());
        WebElement clientDelInfo_new = driver.findElement(By.id("clientsEditList"));
        List<WebElement> tr1 = clientDelInfo_new.findElements(By.tagName("tr"));
        boolean flag2 = false;
        for (WebElement row : tr1) {
            List<WebElement> td2 = row.findElements(By.tagName("td"));

            for (WebElement cell : td2) {
                if (cell.getText().equals("Тест")) {
                    flag2 = true;
                    break;
                }
            }
        }
        assertFalse(flag2);
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        driver.findElement(By.id("endEdit")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(clients, driver.getTitle());
        driver.quit();
    }

    //tests of employees Methods
    @Test
    void addEmployee_then_search_then_update_then_delete() {
        //add
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.get("http://localhost:8080/employees");
        assertEquals(employees, driver.getTitle());

        driver.findElement(By.id("employee_Name_add")).sendKeys("Тест");
        driver.findElement(By.id("address_add")).sendKeys("Тестовый адресс");
        driver.findElement(By.id("phone_add")).sendKeys("666-666-6666");
        driver.findElement(By.id("email_add")).sendKeys("test@test.com");
        driver.findElement(By.id("function__add")).sendKeys("test");
        driver.findElement(By.id("addEmployee")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(employees, driver.getTitle());

        //search
        driver.get("http://localhost:8080/employees");
        assertEquals(employees, driver.getTitle());

        driver.findElement(By.id("employee_Name_search")).sendKeys("Тест");
        driver.findElement(By.id("address_search")).sendKeys("Тестовый адресс");
        driver.findElement(By.id("phone_search")).sendKeys("666-666-6666");
        driver.findElement(By.id("email_search")).sendKeys("test@test.com");
        driver.findElement(By.id("function__search")).sendKeys("test");
        driver.findElement(By.id("searchEmployees")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(employees, driver.getTitle());
        WebElement employeesList = driver.findElement(By.id("employeesList"));
        List<WebElement> cells = employeesList.findElements(By.tagName("td"));

        assertEquals("Тест", cells.get(0).getText());
        assertEquals("Тестовый адресс", cells.get(1).getText());
        assertEquals("666-666-6666", cells.get(2).getText());
        assertEquals("test@test.com", cells.get(3).getText());
        assertEquals("test", cells.get(4).getText());
        assertEquals(employees, driver.getTitle());

        WebElement employeesButton = driver.findElement(By.id("employeesListLink"));
        employeesButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(employees, driver.getTitle());

        //edit
        driver.get("http://localhost:8080/employees");
        assertEquals(employees, driver.getTitle());

        driver.findElement(By.id("employees_edit")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(employees_edit, driver.getTitle());

        WebElement editList = driver.findElement(By.id("employeesEditList"));
        List<WebElement> rows = editList.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> elements = row.findElements(By.tagName("td"));

            for (WebElement cell : elements) {
                if (cell.getText().equals("Тестовый адресс")) {
                    cell.clear();
                    cell.sendKeys("Изменил адресс для теста");

                    WebElement saveButton = row.findElement(By.xpath(".//button[contains(text(), 'Готово!')]"));
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", saveButton);
                    break;
                }
            }
        }

        assertEquals(employees_edit, driver.getTitle());
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        driver.findElement(By.id("endEdit")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(employees, driver.getTitle());

        //test that we changed address of the test person
        WebElement clientInfo_new = driver.findElement(By.id("employeesList"));
        List<WebElement> rows_new = clientInfo_new.findElements(By.tagName("tr"));
        boolean flag = false;
        for (WebElement row : rows_new) {
            List<WebElement> rowElements = row.findElements(By.tagName("td"));

            for (WebElement cell : rowElements) {
                if (cell.getText().equals("Изменил адресс для теста")) {
                    flag = true;
                    break;
                }
            }
        }
        assertTrue(flag);

        //delete
        driver.get("http://localhost:8080/employees");
        assertEquals(employees, driver.getTitle());

        driver.findElement(By.id("employees_edit")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(employees_edit, driver.getTitle());

        WebElement ClientDelInfo = driver.findElement(By.id("employeesEditList"));
        List<WebElement> tr = ClientDelInfo.findElements(By.tagName("tr"));
        outerloop:
        for (WebElement row : tr) {
            List<WebElement> td = row.findElements(By.tagName("td"));

            for (WebElement cell : td) {
                if (cell.getText().equals("Тест")) {
                    WebElement deleteButton = row.findElement(By.xpath(".//button[contains(text(), 'Удалить из базы')]"));
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", deleteButton);
                    break outerloop;
                }
            }
        }

        //test that we deleted the test person
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(employees_edit, driver.getTitle());
        WebElement clientDelInfo_new = driver.findElement(By.id("employeesEditList"));
        List<WebElement> tr1 = clientDelInfo_new.findElements(By.tagName("tr"));
        boolean flag2 = false;
        for (WebElement row : tr1) {
            List<WebElement> td2 = row.findElements(By.tagName("td"));

            for (WebElement cell : td2) {
                if (cell.getText().equals("Тест")) {
                    flag2 = true;
                    break;
                }
            }
        }
        assertFalse(flag2);
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        driver.findElement(By.id("endEdit")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(employees, driver.getTitle());
        driver.quit();
    }

    //tests of services Methods
    @Test
    void addService_then_search_then_update_then_delete() {
        //add
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.get("http://localhost:8080/services");
        assertEquals(services, driver.getTitle());

        driver.findElement(By.id("name_add")).sendKeys("Тест");
        driver.findElement(By.id("cost_add")).sendKeys("666");
        driver.findElement(By.id("addService")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(services, driver.getTitle());

        //search
        driver.get("http://localhost:8080/services");
        assertEquals(services, driver.getTitle());

        driver.findElement(By.id("name_search")).sendKeys("Тест");
        driver.findElement(By.id("cost_search")).sendKeys("666");
        driver.findElement(By.id("searchServices")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(services, driver.getTitle());
        WebElement employeesList = driver.findElement(By.id("servicesList"));
        List<WebElement> cells = employeesList.findElements(By.tagName("td"));

        assertEquals("Тест", cells.get(0).getText());
        assertEquals("666.0", cells.get(1).getText());
        assertEquals(services, driver.getTitle());

        WebElement servicesButton = driver.findElement(By.id("servicesListLink"));
        servicesButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(services, driver.getTitle());

        //edit
        driver.get("http://localhost:8080/services");
        assertEquals(services, driver.getTitle());

        driver.findElement(By.id("services_edit")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(services_edit, driver.getTitle());

        WebElement editList = driver.findElement(By.id("servicesEditList"));
        List<WebElement> rows = editList.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> elements = row.findElements(By.tagName("td"));

            for (WebElement cell : elements) {
                if (cell.getText().equals("Тест")) {
                    cell.clear();
                    cell.sendKeys("Изменил название для теста");

                    WebElement saveButton = row.findElement(By.xpath(".//button[contains(text(), 'Готово!')]"));
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", saveButton);
                    break;
                }
            }
        }

        assertEquals(services_edit, driver.getTitle());
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        driver.findElement(By.id("endEdit")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(services, driver.getTitle());

        //test that we changed address of the test person
        WebElement clientInfo_new = driver.findElement(By.id("servicesList"));
        List<WebElement> rows_new = clientInfo_new.findElements(By.tagName("tr"));
        boolean flag = false;
        for (WebElement row : rows_new) {
            List<WebElement> rowElements = row.findElements(By.tagName("td"));

            for (WebElement cell : rowElements) {
                if (cell.getText().equals("Изменил название для теста")) {
                    flag = true;
                    break;
                }
            }
        }
        assertTrue(flag);

        //delete
        driver.get("http://localhost:8080/services");
        assertEquals(services, driver.getTitle());

        driver.findElement(By.id("services_edit")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(services_edit, driver.getTitle());

        WebElement ClientDelInfo = driver.findElement(By.id("servicesEditList"));
        List<WebElement> tr = ClientDelInfo.findElements(By.tagName("tr"));
        outerloop:
        for (WebElement row : tr) {
            List<WebElement> td = row.findElements(By.tagName("td"));

            for (WebElement cell : td) {
                if (cell.getText().equals("Изменил название для теста")) {
                    WebElement deleteButton = row.findElement(By.xpath(".//button[contains(text(), 'Удалить из базы')]"));
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", deleteButton);
                    break outerloop;
                }
            }
        }

        //test that we deleted the test person
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(services_edit, driver.getTitle());
        WebElement clientDelInfo_new = driver.findElement(By.id("servicesEditList"));
        List<WebElement> tr1 = clientDelInfo_new.findElements(By.tagName("tr"));
        boolean flag2 = false;
        for (WebElement row : tr1) {
            List<WebElement> td2 = row.findElements(By.tagName("td"));

            for (WebElement cell : td2) {
                if (cell.getText().equals("Изменил название для теста")) {
                    flag2 = true;
                    break;
                }
            }
        }
        assertFalse(flag2);
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        driver.findElement(By.id("endEdit")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(services, driver.getTitle());
        driver.quit();
    }

    //tests of serviceHistory Methods
    @Test
    void addServiceHistory_then_search_then_delete() {
        //add
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.get("http://localhost:8080/serviceHistory");
        assertEquals(serviceHistory, driver.getTitle());

        driver.findElement(By.id("1_add")).sendKeys("ООО Фирма Альфа");
        driver.findElement(By.id("2_add")).sendKeys("Сидоров Игорь Петрович");

        driver.findElement(By.id("9_add")).sendKeys("kalinina@example.com");

        driver.findElement(By.id("11_add")).sendKeys("Contract Review");

        driver.findElement(By.id("13_add")).sendKeys("2024-09-05");
        driver.findElement(By.id("14_add")).sendKeys("");
        driver.findElement(By.id("addServiceHistory")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(serviceHistory, driver.getTitle());

        //search
        driver.get("http://localhost:8080/serviceHistory");
        assertEquals(serviceHistory, driver.getTitle());
        driver.findElement(By.id("1_search")).sendKeys("ООО Фирма Альфа");
        driver.findElement(By.id("9_add")).sendKeys("kalinina@example.com");
        driver.findElement(By.id("11_add")).sendKeys("Contract Review");
        driver.findElement(By.id("searchServices")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(serviceHistory, driver.getTitle());
        WebElement employeesList = driver.findElement(By.id("serviceHistoryList"));
        List<WebElement> cells = employeesList.findElements(By.tagName("td"));

        assertEquals("ООО Фирма Альфа", cells.get(0).getText());
        assertEquals("Сидоров Игорь Петрович", cells.get(1).getText());
        assertEquals(serviceHistory, driver.getTitle());

        WebElement serviceHistoryListLink = driver.findElement(By.id("serviceHistoryListLink"));
        serviceHistoryListLink.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(serviceHistory, driver.getTitle());

        //delete
        driver.get("http://localhost:8080/serviceHistory");
        assertEquals(serviceHistory, driver.getTitle());

        WebElement button = driver.findElement(By.id("serviceHistory_edit"));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", button);

        driver.findElement(By.id("serviceHistory_edit")).click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(serviceHistory_edit, driver.getTitle());

        WebElement ClientDelInfo = driver.findElement(By.id("serviceHistoryEditList"));
        List<WebElement> tr = ClientDelInfo.findElements(By.tagName("tr"));
        outerloop:
        for (WebElement row : tr) {
            List<WebElement> td = row.findElements(By.tagName("td"));

            for (WebElement cell : td) {
                if (cell.getText().equals("ООО Фирма Альфа")) {
                    WebElement deleteButton = row.findElement(By.xpath(".//button[contains(text(), 'Удалить из базы')]"));
                    JavascriptExecutor executor1 = (JavascriptExecutor) driver;
                    executor1.executeScript("arguments[0].click();", deleteButton);
                    break outerloop;
                }
            }
        }

        //test that we deleted the test person
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertEquals(serviceHistory_edit, driver.getTitle());

        WebElement clientDelInfo_new = driver.findElement(By.id("serviceHistoryEditList"));
        List<WebElement> tr1 = clientDelInfo_new.findElements(By.tagName("tr"));
        boolean flag2 = false;
        for (WebElement row : tr1) {
            List<WebElement> td2 = row.findElements(By.tagName("td"));

            for (WebElement cell : td2) {
                if (cell.getText().equals("ООО Фирма Альфа")) {
                    flag2 = true;
                    break;
                }
            }
        }
        assertFalse(flag2);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

        WebElement deleteButton = driver.findElement(By.id("endEdit"));
        deleteButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals(serviceHistory, driver.getTitle());
        driver.quit();
    }
}
