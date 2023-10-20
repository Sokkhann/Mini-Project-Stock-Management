package co.istad;

import Testing.Main;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import static co.istad.Helper.*;

public class ProductFeature {
    static Scanner input = new Scanner(System.in);

    // create some helpers class
    // display product in stock
    public static void display(List<Product> products, Integer row, Integer currentPage) {

        Table proTable = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.ALL);
        proTable.addCell(" ".repeat(3) + "ID" + " ".repeat(3));
        proTable.addCell(" ".repeat(3) + "Name" + " ".repeat(3));
        proTable.addCell(" ".repeat(3) + "Price" + " ".repeat(3));
        proTable.addCell(" ".repeat(3) + "QTY" + " ".repeat(3));
        proTable.addCell(" ".repeat(3) + "Import Date" + " ".repeat(3));

        int first = (currentPage - 1) * row;
        int last = Math.min(first + row, products.size());

        for (int i = first; i < last; i++) {
            Integer id = products.get(i).getId();
            String name = products.get(i).getName();
            Double price = products.get(i).getPrice();
            Integer qty = products.get(i).getQuantity();
            LocalDate importDate = products.get(i).getImportDate();

            proTable.addCell(" ".repeat(3) + id + " ".repeat(3));
            proTable.addCell(" ".repeat(3) + name + " ".repeat(3));
            proTable.addCell(" ".repeat(3) + price + " ".repeat(3));
            proTable.addCell(" ".repeat(3) + qty + " ".repeat(3));
            proTable.addCell(" ".repeat(3) + importDate + " ".repeat(3));
        }

        System.out.println(proTable.render());

        Table pageTable = new Table(2,BorderStyle.DESIGN_CURTAIN);
        int totalPages = (int) Math.ceil((double) products.size() / row);
        pageTable.addCell("Page : %d of %s".formatted(currentPage, totalPages +" ".repeat(56)));
        pageTable.addCell("Total Record : %d".formatted(products.size()));

        System.out.println(pageTable.render());

    }

    // write product into stock
    public static void write (List<Product> products) {
        Product product = new Product();
        int lastCode = products.get(products.size()-1).getId();
        System.out.println("Product code: " + (lastCode+1));
        product.setId(lastCode+1);
        String msg = " %d Add successfully...!";
        String sure = "Do you want to add this record? [Y/y] or [N/n]: ";
        addProduct(products, input, product, String.format(msg,product.getId()), sure);
    }

    // read product from stock
    public static void read (List<Product> products) {
        System.out.print("Read by product's code: ");
        int id = Integer.parseInt(input.nextLine());
        boolean isFound = false;
        Product productRead = null;
        for ( Product product : products ){
            if( product.getId().equals(id) ){
                productRead = product;
                isFound = true;
            }
        }
        if(!isFound) {
            message(String.format("Product's code %d invalided...!", id));
        } else{
            Table table=new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.SURROUND);
            showProduct(productRead, table);
        }
    }

    // update product in stock
    public static void update(List<Product> productList){

        boolean isFound = false;
        try {
            System.out.println("Enter ID to update : ");
            Integer newId = Integer.parseInt(input.nextLine());
            for (Product product : productList) {
                if (product.getId().equals(newId)) {
                    Table table = new Table(1,BorderStyle.UNICODE_BOX_DOUBLE_BORDER,ShownBorders.SURROUND);
                    table.addCell(" ID            : "+ newId +" ".repeat(10));
                    table.addCell(" Name          : "+ product.getName() + " ".repeat(5));
                    table.addCell(" Unit price    : "+ product.getPrice() + " ".repeat(5));
                    table.addCell(" Qty           : "+ product.getQuantity() + " ".repeat(5));
                    table.addCell(" Imported Date : "+ LocalDate.now() + " ".repeat(5));
                    System.out.println(table.render());
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                System.out.println("Product with ID : "+ newId +" is not found");
            }
            Product productToUpdate = null;

            for (Product product : productList) {
                if (product.getId().equals(newId)) {
                    productToUpdate = product;
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                System.out.println("Product with ID: " + newId + " is not found");
                return;
            }
            Product product = productToUpdate;

            System.out.println("What do you want to update?");
            Table tableToUpdate = new Table(5, BorderStyle.UNICODE_ROUND_BOX, ShownBorders.SURROUND);
            tableToUpdate.addCell(" ".repeat(2)+"1. All"+" ".repeat(2));
            tableToUpdate.addCell(" ".repeat(2)+"2. Name"+" ".repeat(2));
            tableToUpdate.addCell(" ".repeat(2)+"3. Quantity"+" ".repeat(2));
            tableToUpdate.addCell(" ".repeat(2)+"4. Price"+" ".repeat(2));
            tableToUpdate.addCell(" ".repeat(2)+"5. Back to menu"+" ".repeat(2));
            System.out.println(tableToUpdate.render());
            try {
                System.out.print("Choose option (1-5) : ");
                int op = Integer.parseInt(input.nextLine());
                switch (op) {
                    case 1 -> {
                        try {
                            System.out.print("Enter new product name: ");
                            String newName = input.nextLine();
                            System.out.print("Enter new quantity: ");
                            Integer newQuantity = Integer.parseInt(input.nextLine());
                            System.out.print("Enter new price: ");
                            Double newPrice = Double.parseDouble(input.nextLine());

                            product.setName(newName);
                            product.setQuantity(newQuantity);
                            product.setPrice(newPrice);

                            Table updatedTable = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                            updatedTable.addCell(" ID            : " + newId + " ".repeat(5));
                            updatedTable.addCell(" Name          : " + newName + " ".repeat(5));
                            updatedTable.addCell(" Unit price    : " + newPrice + " ".repeat(5));
                            updatedTable.addCell(" Qty           : " + newQuantity + " ".repeat(5));
                            updatedTable.addCell(" Imported Date : " + LocalDate.now() + " ".repeat(5));
                            System.out.println(updatedTable.render());

                            System.out.print("Are you sure to add this record? [Y/y] or [N/n] : ");
                            String options = input.nextLine();
                            switch (options) {
                                case "y", "Y" -> {
                                    productList.add(productToUpdate);
                                    System.out.println("Product added successfully.");
                                }
                                case "n", "N" -> System.out.println("Product is not added");
                                default -> System.out.println("Invalid options.");
                            }
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                        }
                    }


                    case 2 -> {
                        try {
                            System.out.print("Enter new product name : ");
                            String newName = input.nextLine();
                            product.setName(newName);
                            Table updatedTable = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                            updatedTable.addCell(" ID            : " + newId + " ".repeat(5));
                            updatedTable.addCell(" Name          : " + newName + " ".repeat(5));
                            updatedTable.addCell(" Unit price    : " + product.getPrice() + " ".repeat(5));
                            updatedTable.addCell(" Qty           : " + product.getQuantity() + " ".repeat(5));
                            updatedTable.addCell(" Imported Date : " + LocalDate.now() + " ".repeat(5));
                            System.out.println(updatedTable.render());
                            System.out.print( "Are you sure to add this record? [Y/y] or [N/n] : ");
                            String options = input.nextLine();
                            switch (options) {
                                case "y","Y" -> {
                                    productList.add(productToUpdate);
                                    System.out.println("Product added successfully.");
                                }
                                case "n","N" -> System.out.println("Product is not added");
                                default -> System.out.println("Invalid options.");
                            }
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                        }
                    }
                    case 3 -> {
                        try {
                            System.out.println("Enter new product Price : ");
                            Double newPrice = Double.parseDouble(input.nextLine());
                            product.setPrice(newPrice);
                            Table updatedTable = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                            updatedTable.addCell(" ID            : " + newId + " ".repeat(5));
                            updatedTable.addCell(" Name          : " + product.getName() + " ".repeat(5));
                            updatedTable.addCell(" Unit price    : " + newPrice + " ".repeat(5));
                            updatedTable.addCell(" Qty           : " + product.getQuantity() + " ".repeat(5));
                            updatedTable.addCell(" Imported Date : " + LocalDate.now() + " ".repeat(5));
                            System.out.println(updatedTable.render());
                            System.out.print( "Are you sure to add this record? [Y/y] or [N/n] : ");
                            String options = input.nextLine();
                            switch (options) {
                                case "y","Y" -> {
                                    productList.add(productToUpdate);
                                    System.out.println("Product added successfully.");
                                }
                                case "n","N" -> System.out.println("Product is not added");
                                default -> System.out.println("Invalid options.");
                            }
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                        }
                    }
                    case 4 -> {
                        try {
                            System.out.println("Enter new product Price : ");
                            Integer newProductQty = Integer.parseInt(input.nextLine());
                            product.setQuantity(newProductQty);
                            Table updatedTable = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                            updatedTable.addCell(" ID            : " + newId + " ".repeat(5));
                            updatedTable.addCell(" Name          : " + product.getName() + " ".repeat(5));
                            updatedTable.addCell(" Unit price    : " + product.getPrice() + " ".repeat(5));
                            updatedTable.addCell(" Qty           : " + newProductQty + " ".repeat(5));
                            updatedTable.addCell(" Imported Date : " + LocalDate.now() + " ".repeat(5));
                            System.out.println(updatedTable.render());
                            System.out.print( "Are you sure to add this record? [Y/y] or [N/n] : ");
                            String options = input.nextLine();
                            switch (options) {
                                case "y","Y" -> {
                                    productList.add(productToUpdate);
                                    System.out.println("Product added successfully.");
                                }
                                case "n","N" -> System.out.println("Product is not added");
                                default -> System.out.println("Invalid options.");
                            }
                        } catch ( Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 5 -> System.out.println("Back to menu : ");
                    default -> throw new IllegalStateException("Unexpected value: " + op);
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


    // delete product from stock
    public static void delete(List<Product> products){
        System.out.print("Please input id for delete: ");
        int id = Integer.parseInt(input.nextLine());
        boolean isFound = false;
        for (Product product : products) {
            if(product.getId().equals(id)){
                products.remove(product);
                Table table=new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.SURROUND);
                showProduct(product,table);
                System.out.print("Are you sure want to add this record? [Y/y] or [N/n]: ");
                if (input.nextLine().equalsIgnoreCase("y")){
                    System.out.println();
                    products.remove(product);
                    message(String.format("Product %d delete successfully...!",id));
                    System.out.println();
                    if(products.size() == 0){
                        System.out.println("Product is empty...!");
                    }
                    isFound = true;
                    break;
                }else {
                    message(String.format("Cancel!"));
                    isFound = true;
                    break;
                }
            }
        }
        if(!isFound) {
            message(String.format("Product's %d invalided...", id));
        }
    }

    // searching product
    public static void search(List<Product> products, int row, int currentPage){
        List<Product> productFound = new ArrayList<>();
        AtomicBoolean isFound = new AtomicBoolean(false);
        System.out.print("Search by product's name: ");
        String searchName = input.nextLine().toLowerCase();
        products.forEach( product -> {
            if( product.getName().toLowerCase().contains( searchName ) ){
                productFound.add( product );
                isFound.set(true);
            }
        } );
        if(!isFound.get()){
            message("Product not found...!");
        }
        else{
            message(String.format("Product found for [%s]: %d",searchName, productFound.size()));
            Table tableDisplay = new Table(5,BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.ALL);
            tableDisplay.addCell(" ".repeat(3) + "Code" + " ".repeat(3));
            tableDisplay.addCell(" ".repeat(3) + "Product Name" + " ".repeat(3));
            tableDisplay.addCell(" ".repeat(3) + "Unit Price" + " ".repeat(3));
            tableDisplay.addCell( " ".repeat(3) + "Quantity" + " ".repeat(3));
            tableDisplay.addCell(" ".repeat(3) + "Import Date" + " ".repeat(3));
            int first = (currentPage - 1) * row;
            int last = Math.min(first + row, productFound.size());
            for (int i = first; i < last; i++) {
                int id = productFound.get(i).getId();
                String name = productFound.get(i).getName();
                double price = productFound.get(i).getPrice();
                int qty = productFound.get(i).getQuantity();
                LocalDate importDate = productFound.get(i).getImportDate();
                tableDisplay.addCell(" ".repeat(3) + id + " ".repeat(3));
                tableDisplay.addCell(" ".repeat(3) + name + " ".repeat(3));
                tableDisplay.addCell(" ".repeat(3) + price + " ".repeat(3));
                tableDisplay.addCell( " ".repeat(3) + qty + " ".repeat(3));
                tableDisplay.addCell(" ".repeat(3) + importDate + " ".repeat(3));
            }
            System.out.println(tableDisplay.render());
            Table tablePage = new Table(2,BorderStyle.DESIGN_CURTAIN);
            int totalPages = (int) Math.ceil((double) productFound.size() / row);
            tablePage.addCell("Page : %d of %s".formatted(currentPage, totalPages +" ".repeat(56)));
            tablePage.addCell("Total Record : %d".formatted(productFound.size()));
            System.out.println(tablePage.render());
        }
    }

    // goto
    public static int gotoPage(List<Product> products, int row, int currentPage){
        System.out.print("Enter page you want to go: ");
        int pageToGo = input.nextInt();
        int totalPages = (int) Math.ceil((double) products.size() / row);
        if (pageToGo > 0 && pageToGo <= totalPages) {
            currentPage = pageToGo;
            display(products, row, currentPage);
        } else {
            message(String.format("Invalid page %d. Please enter page from 1 to %d",pageToGo, totalPages));
        }
        return currentPage;
    }

    public static int setRecord(){
        System.out.print("Please enter record for display: ");
        int record = Integer.parseInt(input.nextLine());
        message(String.format("    Set page to %d record successfully...!    " ,record));
        return record;
    }

    public static void helping () {
        Table table1 = new Table(1, BorderStyle.CLASSIC_WIDE, ShownBorders.SURROUND);
        table1.addCell("1" + " ".repeat(3) + "Press" + " ".repeat(3) + "*" + " ".repeat(2) + " : Display all record of products");
        table1.addCell("2" + " ".repeat(3) + "Press" + " ".repeat(3) + "w" + " ".repeat(2) + " : Add new product");
        table1.addCell(" " + " ".repeat(3) + "Press" + " ".repeat(3) + "w#" + " ".repeat(1) + " : shortcut for add new product");
        table1.addCell("3" + " ".repeat(3) + "Press" + " ".repeat(3) + "r" + " ".repeat(2) + " : read content any content");
        table1.addCell(" " + " ".repeat(3) + "Press" + " ".repeat(3) + "r#" + " ".repeat(1) + " : shortcut for reading product by ID");
        table1.addCell("4" + " ".repeat(3) + "Press" + " ".repeat(3) + "u" + " ".repeat(2) + " : Update Data");
        table1.addCell("5" + " ".repeat(3) + "Press" + " ".repeat(3) + "d" + " ".repeat(2) + " : Delete Data");
        table1.addCell(" " + " ".repeat(3) + "Press" + " ".repeat(3) + "d#" + " ".repeat(1) + " : shortcut for delete product b ID");
        table1.addCell("6" + " ".repeat(3) + "Press" + " ".repeat(3) + "f" + " ".repeat(2) + " : Display First Page");
        table1.addCell("7" + " ".repeat(3) + "Press" + " ".repeat(3) + "p" + " ".repeat(2) + " : Display Previous Page");
        table1.addCell("8" + " ".repeat(3) + "Press" + " ".repeat(3) + "n" + " ".repeat(2) + " : Display Next Page");
        table1.addCell("9" + " ".repeat(3) + "Press" + " ".repeat(3) + "l" + " ".repeat(2) + " : Display Last Page");
        table1.addCell("10" + " ".repeat(2) + "Press" + " ".repeat(3) + "s" + " ".repeat(2) + " : Search Product by Name");
        table1.addCell("11" + " ".repeat(2) + "Press" + " ".repeat(3) + "h" + " ".repeat(2) + " : Help");
        System.out.println(table1.render());
        System.out.println();
    }
}
