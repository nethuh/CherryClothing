package lk.ijse.CherryClothing.view.tm;

public class SupplierTM implements Comparable<SupplierTM>{
    private String supId;
    private String name;
    private String contact;

    public SupplierTM(String supId , String name , String contact ) {
        this.supId = supId;
        this.name = name;
        this.contact = contact;

    }
    public String getSupId(){

        return supId;
    }
    public void setSupId(String supId){

        this.supId = supId;
    }
    public String getName(){

        return name;
    }
    public void setName (String name){

        this.name = name;
    }
    public String getContact(){
        return contact;
    }
    public void setContact (String contact){
        this.contact = contact;
    }



    @Override
    public String toString(){
        return "Supplier {" + "supId='" + supId + '\'' + ", name='" + name + '\''+",contact='" + contact +'}';
    }
    @Override
    public int compareTo(SupplierTM o) {
        return supId.compareTo(o.getSupId());
    }
}
