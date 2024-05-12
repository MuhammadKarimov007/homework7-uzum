package org.example;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        // возьмем путь к каталогу от пользователя
        File path = HomeworkImpl.askDirPath();

        // проверим, существует ли каталог и является ли путь каталогом или файлом.
        while (HomeworkImpl.checkDir(path) != 0) {
            path = HomeworkImpl.askDirPath();
        }

        // Фильтруем файлы и распечатываем их в отформатированном виде.
        HomeworkImpl.filterFilesAt(path);

    }
}