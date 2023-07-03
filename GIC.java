import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class GIC {
    static Map<String, List<String>> calenders = new HashMap<>();
    static List<String> countries = new ArrayList<>();
    static String countryName;

    public static void main(String[] args) {
        loadFiles();

        System.out.println("Welcome to GIC Calendar Calculator");

        Scanner scanner = new Scanner(System.in);
        Integer country;
        while (true) {
            System.out.println("Please choose a country:");
            countries.forEach(s -> System.out.println("[" + (countries.indexOf(s) + 1) + "] " + s));

            try {
                country = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter an integer");
                continue;
            }
            if (country > countries.size() || country < 1) {
                System.out.println("select a valid index");
            } else {
                break;
            }
        }
        countryName = countries.get(country - 1);


        while (true) {
            System.out.println("Please choose an action:\n[1] Check a date\n[2] Calculate next business day");
            Integer option = Integer.parseInt(scanner.nextLine());
            String date;

            switch (option) {
                case 1:
                    System.out.println("Please enter a date to check in yyyyMMdd format");
                    date = scanner.nextLine();
                    while (true) {
                        checkDateMethod(date);
                        System.out.println("Please enter a date to check in yyyyMMdd format or please enter # to end");
                        date = scanner.nextLine();
                        if (Objects.equals(date, "#")) {
                            return;
                        }
                    }
                case 2:
                    System.out.println("Please enter a date in yyyyMMdd format");
                    date = scanner.nextLine();
                    while (true) {
                        nextBusinessDateMethod(date);
                        System.out.println("Please enter a date in yyyyMMdd format or please enter # to end");
                        date = scanner.nextLine();
                        if (Objects.equals(date, "#")) {
                            return;
                        }
                    }
                default:
                    System.out.println("wrong input, select again");
                    break;
            }
        }
    }

    static void loadFiles(){
        try {
            String folderPath = "countries";
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isFile()) {
                    String countryName = file.getName().replace(".txt", "");
                    countries.add(countryName);

                    String filePath = folderPath + "/" + file.getName();
                    List<String> lines = Files.readAllLines(Paths.get(filePath));
                    calenders.put(countryName, lines);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void checkDateMethod(String date) {
        if (calenders.get(countryName).contains(date)) {
            System.out.println(date + " is a public holiday");
            return;
        }

        String year;
        String month;
        String day;
        Calendar calendar = Calendar.getInstance();
        try {
            year = date.substring(0, 4);
            month = date.substring(4, 6);
            day = date.substring(6);
            calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day)); // Year, Month (0-11), Day
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Enter a valid date");
            return;
        }

        if (isDateValid(year + "-" + month + "-" + day)) {
            System.out.println("Enter a valid date");
            return;
        }

        boolean isWeekend = isWeekend(calendar);
        if (isWeekend) {
            System.out.println(date + " is a weekend");
        } else {
            System.out.println(date + " is a working day");
        }
    }

    static void nextBusinessDateMethod(String date) {
        String year;
        String month;
        String day;
        Calendar calendar = Calendar.getInstance();
        try {
            year = date.substring(0, 4);
            month = date.substring(4, 6);
            day = date.substring(6);
            calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day)); // Year, Month (0-11), Day
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Enter a valid date");
            return;
        }

        if (isDateValid(year + "-" + month + "-" + day)) {
            System.out.println("Enter a valid date");
            return;
        }

        calendar.add(calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate;
        while (true) {
            if (isWeekend(calendar)) {
                calendar.add(calendar.DAY_OF_YEAR, 1);
                continue;
            }

            formattedDate = dateFormat.format(calendar.getTime());

            if (calenders.get(countryName).contains(formattedDate)) {
                calendar.add(calendar.DAY_OF_YEAR, 1);
                continue;
            }

            break;

        }
        formattedDate = dateFormat.format(calendar.getTime());
        System.out.println("The next business day is " + formattedDate);
    }

    public static boolean isWeekend(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    public static boolean isDateValid(String dateString) {
        try {
            LocalDate.parse(dateString);
            return false;
        } catch (DateTimeParseException e) {
            return true;
        }
    }

}
