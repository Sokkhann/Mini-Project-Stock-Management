package co.istad;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static co.istad.Helper.*;
import static co.istad.Pagination.*;
import static co.istad.ProductFeature.*;

public class StockManagement {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // create list to store product
        List<Product> products = new ArrayList<>();
        Product product = new Product();

        // default row 4
        Integer row = 4;
        // start from first page
        Integer currentPage = 1;

        System.out.println(" " +
                "██████╗███████╗████████╗ █████╗ ██████╗          ██╗ █████╗ ██╗   ██╗ █████╗     \n" +
                "██╔════╝██╔════╝╚══██╔══╝██╔══██╗██╔══██╗         ██║██╔══██╗██║   ██║██╔══██╗    \n" +
                "██║     ███████╗   ██║   ███████║██║  ██║         ██║███████║██║   ██║███████║    \n" +
                "██║     ╚════██║   ██║   ██╔══██║██║  ██║    ██   ██║██╔══██║╚██╗ ██╔╝██╔══██║    \n" +
                "╚██████╗███████║   ██║   ██║  ██║██████╔╝    ╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║    \n" +
                " ╚═════╝╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═════╝      ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝    \n");

        defaultProduct(products);

        do {
            // table
            Table table = new Table(9, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.SURROUND);
            System.out.println("STOCK MANAGEMENT SYSTEM");

            table.addCell(" ".repeat(2) + " *)Display" + " ".repeat(2));
            table.addCell(" ".repeat(2) + "| W)rite" + " ".repeat(2));
            table.addCell(" ".repeat(2) + "| R)ead" + " ".repeat(2));
            table.addCell(" ".repeat(2) + "| U)pdate" + " ".repeat(2));
            table.addCell(" ".repeat(2) + "| D)elete" + " ".repeat(2));
            table.addCell(" ".repeat(2) + "| F)irst" + " ".repeat(2));
            table.addCell(" ".repeat(2) + "| P)revious" + " ".repeat(2));
            table.addCell(" ".repeat(2) + "| N)ext" + " ".repeat(2));
            table.addCell(" ".repeat(2) + "| L)ast" + " ".repeat(2));
            table.addCell(" ".repeat(2) + " S)earch" + " ".repeat(2));
            table.addCell(" ".repeat(2) + "| G)oto" + " ".repeat(2));
            table.addCell(" ".repeat(2) + "| Se)t tow" + " ".repeat(2));
            table.addCell(" ".repeat(2) + "| H)elp" + " ".repeat(2));
            table.addCell(" ".repeat(2) + "| E)xit" + " ".repeat(2));
            System.out.println(table.render());

            // option
            String option;
            System.out.print("Command -> ");
            option = input.nextLine();
            switch (option) {

                // display option
                case "*" -> {
                    display(products, row, currentPage);
                }

                // add product into stock
                case "W", "w" -> {
                    write(products);
                }

                case "R", "r" -> {
                    read(products);
                }

                case "U", "u" -> {
                    update(products);
                }

                case "D", "d" -> {
                    delete(products);
                }

                case "F", "f" -> {
                    currentPage = firstPage(products, row, currentPage);
                }

                case "P", "p" -> {
                    currentPage = previousPage(products, row, currentPage);
                }

                case "N", "n" -> {
                    currentPage = nextPage(products, row, currentPage);
                }

                case "L", "l" -> {
                    currentPage = last(products, row, currentPage);
                }

                case "S", "s" -> {
                    search(products, row, currentPage);
                }

                case "G", "g" -> {
                    currentPage = gotoPage(products, row, currentPage);
                }

                case "Se", "se", "sE" -> {
                    row = setRecord();
                }

                case "H", "h" -> {
                    helping();
                }

                case "E", "e" -> {
                    System.out.println("Thank you...");
                    System.exit(0);
                }

                default -> {
                    String[] shortcut = option.split("#");
                    switch (shortcut[0].toUpperCase()){
                        case "W" -> {
                            writeShortCut(products,shortcut);
                        }
                        case "R" -> {
                            readShortCut(products, shortcut);
                        }
                        case "D" -> {
                            deleteShortCut(products, shortcut);
                        }
                        default -> {
                            message("Please choose option above: ");
                        }
                    }
                }
            }

        } while (true);
    }
}
