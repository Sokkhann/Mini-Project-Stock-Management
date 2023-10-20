package co.istad;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Helper {
    static Scanner input = new Scanner(System.in);

    public static void defaultProduct (List<Product> products) {
        // add some default products into our stock
        products.add(new Product(1, "Lay's", 0.50, 33, LocalDate.now()));
        products.add(new Product(2, "Lactasoy", 0.50, 120, LocalDate.now()));
        products.add(new Product(3, "Sandwich", 1.20, 100, LocalDate.now()));
        products.add(new Product(4, "Yogurt", 0.50, 120, LocalDate.now()));
        products.add(new Product(5, "Pepsi", 0.90, 300, LocalDate.now()));
        products.add(new Product(6, "Milk", 0.50, 530, LocalDate.now()));
        products.add(new Product(7, "Pie", 1.00, 420, LocalDate.now()));
        products.add(new Product(8, "KitKat", 3.00, 203, LocalDate.now()));
    }

    public static boolean confirm(List<Product> products, Product product, String msg, String sure) {
        Table tableConfirm = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.SURROUND);
        System.out.println();
        tableConfirm.addCell("Product ID        :"+  product.getId());
        tableConfirm.addCell("Product name      : " + product.getName());
        tableConfirm.addCell("Unit price        : " + product.getPrice());
        tableConfirm.addCell("Quantity          : " + product.getQuantity());
        tableConfirm.addCell("Import date       : " + product.getImportDate());

        System.out.println(tableConfirm.render());
        System.out.println();

        System.out.print("Are you sure want to delete this record? [Y/y] or [N/n]: ");
        if (input.nextLine().equalsIgnoreCase("y")) {
            System.out.println();
            message(String.format(msg));
            products.add(product);
            System.out.println();
            return true;
        } else {
            message(String.format("Cancel!"));
            return false;
        }
    }

    public static void addProduct(List<Product> products, Scanner sc, Product product,String msg,String sure) {
        System.out.print("Product's name: ");
        product.setName(sc.nextLine());
        System.out.print("Product's price: ");
        product.setPrice(Double.parseDouble(sc.nextLine()));
        System.out.print("Product's quantity: ");
        product.setQuantity(Integer.parseInt(sc.nextLine()));
        product.setImportDate(LocalDate.now());
        confirm(products, product,msg,sure);
    }

    public static void message(String msg) {
        Table tableMessage = new Table(1, BorderStyle.DESIGN_CAFE_WIDE, ShownBorders.SURROUND);
        tableMessage.addCell(msg);
        System.out.println(tableMessage.render());
    }

    public static void showProduct(Product productRead, Table table) {
        System.out.println();
        table.addCell( "ID                 :" + productRead.getId() );
        table.addCell( "Name               :"+ productRead.getName() );
        table.addCell( "Unit Price         :" + productRead.getPrice() );
        table.addCell( "Qty                :" + productRead.getQuantity() );
        table.addCell( "Imported Date      :" + productRead.getImportDate() );
        System.out.println( table.render() );
        System.out.println();
    }

    public static void writeShortCut(List<Product> products, String[] shortcut){
        Product product = new Product();
        String[] newProduct = shortcut[1].split("-");
        product.setId(products.size() + 1);
        product.setName( newProduct[0] );
        product.setPrice(Double.parseDouble( newProduct[1]));
        product.setQuantity(Integer.parseInt( newProduct[2] ));
        product.setImportDate(LocalDate.now());
        String msg = "    %d was added successfully...!    ";
        String sure = "Are you sure want to add this record? [Y/y] or [N/n]: ";
        confirm(products, product, String.format(msg,product.getId()),sure);
    }

    public static void readShortCut(List<Product> products, String[] shortcut){
        int code = Integer.parseInt( shortcut[1] );
        boolean isFound = false;
        for( Product product : products ){
            if( product.getId().equals(code) ){
                Table table=new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.SURROUND);
                showProduct(product, table);
                isFound = true;
            }
        }
        if( !isFound ) {
            message(String.format("Product with code %d nod found...!",code));
        }
    }

    public static void deleteShortCut(List<Product> products, String[] shortcut){
        int code = Integer.parseInt( shortcut[1] );
        boolean isFound = false;
        for (Product product : products) {
            if (product.getId().equals(code)) {
                Table table=new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.SURROUND);
                showProduct(product, table);
                Scanner s = new Scanner(System.in);
                System.out.print("Are you sure want to add this record? [Y/y] or [N/n]: ");
                if (s.nextLine().equalsIgnoreCase("y")){
                    System.out.println();
                    products.remove(product);
                    System.out.println();
                    isFound = true;
                    break;
                }else {
                    message(String.format("Cancel!"));
                    isFound = true;
                    break;
                }
            }
        }
        if(!isFound){
            message(String.format("Product's %d invalided...", code));
        }
    }
}
