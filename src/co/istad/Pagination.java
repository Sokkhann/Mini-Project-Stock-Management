package co.istad;

import java.util.List;

import static co.istad.Helper.message;
import static co.istad.ProductFeature.display;

public class Pagination {

    // first page
    public static int firstPage(List<Product> products, Integer row, Integer currentPage) {
        if (currentPage == 1) {
            message("First Page");
            display(products, row,currentPage);
        } else {
            currentPage = 1;
            display(products, row,currentPage);
        }
        return currentPage;
    }

    // back to previous page
    public static int previousPage(List<Product> products, Integer row, Integer currentPage) {
        if (currentPage > 1) {
            currentPage--;
            display(products, row, currentPage);
        }else {
            currentPage = (int) Math.ceil((double) products.size() / row);
            display(products, row, currentPage);
        }
        return currentPage;
    }

    // go to next page
    public static int nextPage(List<Product> products, int row, int currentPage) {
        int totalPages = (int) Math.ceil((double) products.size() / row);
        if (currentPage < totalPages) {
            currentPage++;
            display(products, row, currentPage);
        }else {
            currentPage = 1;
            display(products, row, currentPage);
        }
        return currentPage;
    }

    // go to last page
    public static int last(List<Product> products, int row, int currentPage) {
        int totalPages = (int) Math.ceil((double) products.size() / row);
        if (currentPage == totalPages) {
            message("Last Page");
            display(products, row, currentPage);
        } else {
            currentPage = totalPages;
            display(products, row, currentPage);
        }
        return currentPage;
    }
}
