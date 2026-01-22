import java.util.Vector;
import java.util.Enumeration;

// --- Lớp Movie: Lưu trữ thông tin phim ---
class Movie {
    public static final int CHILDRENS = 2;
    public static final int NEW_RELEASE = 1;
    public static final int REGULAR = 0;

    private String _title;
    private int _priceCode;

    public Movie(String title, int priceCode) {
        _title = title;
        _priceCode = priceCode;
    }

    public int getPriceCode() {
        return _priceCode;
    }

    public void setPriceCode(int arg) {
        _priceCode = arg;
    }

    public String getTitle() {
        return _title;
    }
}

// --- Lớp Rental: Đại diện cho một khoản thuê ---
class Rental {
    private Movie _movie;
    private int _daysRented;

    public Rental(Movie movie, int daysRented) {
        _movie = movie;
        _daysRented = daysRented;
    }

    public int getDaysRented() {
        return _daysRented;
    }

    public Movie getMovie() {
        return _movie;
    }
}

// --- Lớp Customer: Quản lý khách hàng và hóa đơn ---
class Customer {
    private String _name;
    private Vector _rentals = new Vector();

    public Customer(String name) {
        _name = name;
    }

    public void addRental(Rental arg) {
        _rentals.addElement(arg);
    }

    public String getName() {
        return _name;
    }

    // Phương thức tạo hóa đơn chi tiết
    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Enumeration rentals = _rentals.elements();

        String result = "Rental Record for " + getName() + "\n";

        while (rentals.hasMoreElements()) {
            double thisAmount = 0;
            Rental each = (Rental) rentals.nextElement();

            // Xác định số tiền cho từng loại phim
            // switch (each.getMovie().getPriceCode()) {
            //     case Movie.REGULAR:
            //         thisAmount += 2;
            //         if (each.getDaysRented() > 2)
            //             thisAmount += (each.getDaysRented() - 2) * 1.5;
            //         break;

            //     case Movie.NEW_RELEASE:
            //         thisAmount += each.getDaysRented() * 3;
            //         break;

            //     case Movie.CHILDRENS:
            //         thisAmount += 1.5;
            //         if (each.getDaysRented() > 3)
            //             thisAmount += (each.getDaysRented() - 3) * 1.5;
            //         break;
            // Thay thế khối switch bằng lời gọi hàm
            thisAmount = amountFor(each);
            

            // Cộng điểm thưởng
            frequentRenterPoints++;

            // Thưởng thêm cho phim mới thuê trên 1 ngày
            if (each.getMovie().getPriceCode() == Movie.NEW_RELEASE
                    && each.getDaysRented() > 1)
                frequentRenterPoints++;

            // Hiển thị chi tiết từng phim
            result += "\t" + each.getMovie().getTitle()
                    + "\t" + thisAmount + "\n";

            totalAmount += thisAmount;
        }

        // Phần chân hóa đơn
        result += "Amount owed is " + totalAmount + "\n";
        result += "You earned " + frequentRenterPoints + " frequent renter points";

        return result;
    }
    
        // Thêm hàm này vào lớp Customer
private double amountFor(Rental eRental) {
    double result = 0;
    switch (eRental.getMovie().getPriceCode()) {
        case Movie.REGULAR:
            result += 2;
            if (eRental.getDaysRented() > 2)
                result += (eRental.getDaysRented() - 2) * 1.5;
            break;
        case Movie.NEW_RELEASE:
            result += eRental.getDaysRented() * 3;
            break;
        case Movie.CHILDRENS:
            result += 1.5;
            if (eRental.getDaysRented() > 3)
                result += (eRental.getDaysRented() - 3) * 1.5;
            break;
    }
    return result;
}
}

// --- Lớp chạy chương trình ---
public class Main {
    public static void main(String[] args) {
        // 1. Tạo các bộ phim mẫu
        Movie m1 = new Movie("Avatar 2", Movie.NEW_RELEASE);
        Movie m2 = new Movie("Lion King", Movie.CHILDRENS);
        Movie m3 = new Movie("The Godfather", Movie.REGULAR);

        // 2. Tạo khách hàng
        Customer customer = new Customer("Nguyen Van A");

        // 3. Thêm các khoản thuê
        customer.addRental(new Rental(m1, 3));
        customer.addRental(new Rental(m2, 5));
        customer.addRental(new Rental(m3, 2));

        // 4. In hóa đơn
        System.out.println(customer.statement());
    }
}
