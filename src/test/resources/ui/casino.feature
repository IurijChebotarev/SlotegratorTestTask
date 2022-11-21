Feature: Casino

  Scenario: Проверка авторизации
    Given Открываем сайт
    When Авторизовываемся в админке под юзером "admin1" и паролем "[9k<k8^z!+$$GkuP"
    Then Dashboard - Casino страница открыта

  Scenario: Проверка сортировки таблицы Player Management
    Given Открываем сайт
    When Авторизовываемся в админке под юзером "admin1" и паролем "[9k<k8^z!+$$GkuP"
    Then Dashboard - Casino страница открыта
    When Нажимаем на таб "users" и выбираем "player/admin" из списка
    Then Таблица Player management загрузилась

  Scenario: Проверка сортировки таблицы Player Management
    Given Открываем сайт
    When Авторизовываемся в админке под юзером "admin1" и паролем "[9k<k8^z!+$$GkuP"
    Then Dashboard - Casino страница открыта
    When Нажимаем на таб "users" и выбираем "player/admin" из списка
    Then Таблица Player management загрузилась
    #Следующий степ добавлен, чтобы сократить кол-во записей в таблице, для ускорения проверки сортировки
    When Фильтруем таблицу Player management по колонке "email" с значением "@mail.ru"
    And Сортируем таблицу Player management по колонке с номером "10"
    Then Проверяем что сортировка по колонке "10" корректна