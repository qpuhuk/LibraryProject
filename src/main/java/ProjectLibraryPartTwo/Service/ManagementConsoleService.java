package ProjectLibraryPartTwo.Service;

import ProjectLibraryPartTwo.DAO.LibraryDAO;
import ProjectLibraryPartTwo.DAO.SQLLibraryDAO;
import ProjectLibraryPartTwo.Entity.Book;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public final class ManagementConsoleService {
    private final LibraryDAO libraryDAO = new SQLLibraryDAO();

    public void start() {
        try (Scanner scannerInt = new Scanner(System.in); Scanner scannerLine = new Scanner(System.in)) {
            int result;
            do {
                System.out.println("******************************\n" +
                        "Выберите одно из предложенных действий. Для выбора введите цифру, соответствующую вашему запросу -> " +
                        "\n 1. Вывести все книги" +
                        "\n 2. Добавление книги" +
                        "\n 3. Удаление книги" +
                        "\n 4. Редактирование книги" +
                        "\n 0. выход");
                result = scannerInt.nextInt();
                switch (result) {
                    case 1: {
                        System.out.println("******************************\n" +
                                "В какой сортировке вы желаете увидеть список доступных книг? " +
                                "Для выбора введите цифру, соответствующую вашему запросу ->");
                        System.out.println("1. По алфавиту по возрастанию.");
                        System.out.println("2. По алфавиту по убыванию.");
                        System.out.println("3. По добавлению (сперва новые, затем старые).");
                        libraryDAO.writeAllBooks(scannerInt.nextInt());
                        break;
                    }
                    case 2:
                        System.out.println("Чтобы добавить книгу в библиотеку введите ID, название книги," +
                                " жанр книги, дату написания в формате (yyyy-mm-dd), ID автора через Enter");
                        System.out.println("Введите ID ->");
                        int id = scannerInt.nextInt();
                        List<Book> allBooksInLibrary = libraryDAO.getAllBooks();
                        boolean check = allBooksInLibrary.stream().anyMatch(book -> book.getId() == id);
                        if (check) {
                            System.out.println("Книга с таким ID уже существует, попробуйте другой ID");
                            break;
                        }
                        System.out.println("Введите название книги ->");
                        String title = scannerLine.nextLine();
                        System.out.println("Введите жанр книги из списка (COMEDY,BIOGRAPHY,FANTASTIC,ACTION,MELODRAMA,CHILDREN_BOOK) ->");
                        String genre = scannerLine.nextLine();
                        boolean truth = Arrays.stream(Book.Genre.values()).anyMatch(t -> t.name().equals(genre));
                        if (!truth) {
                            System.out.println("Такого жанра нет в списке, повторите");
                            break;
                        }
                        System.out.println("Введите ID автора ->");
                        int idAuthor = scannerInt.nextInt();
                        System.out.println("Введите дату публикации в формате (yyyy-mm-dd) ->");
                        String date = scannerLine.nextLine();
                        if (!date.matches("[0-9]{4}[-]+[0-9]{2}[-]+[0-9]{2}")) {
                            System.out.println("Некорректная дата или введена не по шаблону");
                            break;
                        }
                        System.out.println("***************\nИдет обработка....");
                        TimeUnit.SECONDS.sleep(3);
                        boolean resultAdd = libraryDAO.addBookByIdTitleGenre(id, title, genre, idAuthor, date);
                        System.out.println(resultAdd ? "Книга добавлена" : "Добавление невозможно");
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("Что-нибудь еще?");
                        TimeUnit.SECONDS.sleep(1);
                        break;
                    case 3:
                        System.out.println("Чтобы удалить книгу из библиотеки введите ID книги ->");
                        boolean resultDelete = libraryDAO.deleteBookById(scannerInt.nextInt());
                        System.out.println("Идет поиск в базе...");
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("Ожидайте...");
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(resultDelete ? "Книга удалена" : "Книги с таким ID не существует");
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("Что-нибудь еще?");
                        TimeUnit.SECONDS.sleep(1);
                        break;
                    case 4:
                        System.out.println("Для редактирования книги вам необходимо ввести ID книги, новое название книги и новый жанр");
                        boolean resultCorrect = libraryDAO.correctBookByIdNewTitleNewGenre(scannerInt.nextInt(), scannerLine.nextLine(), scannerLine.nextLine());
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println(resultCorrect ? "Книга успешно отредактирована" : "Книга не может быть отредактирована, повторите позже!");
                        break;
                    case 0:
                        System.out.println("Заходите к нам еще!!!");
                        break;
                    default:
                        System.out.println("Неизвестная команда");
                }
            } while (result != 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
