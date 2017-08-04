package models

class Item(itemId: Int, itemNm :String, resume:String) {
  def findAll() : List[Item] = {
    var list = List(
        new Item(1, "Nm1","resume1")
        ,new Item(2, "Nm2","resume2")
        ,new Item(3, "Nm3","resume3")
        ,new Item(4, "Nm4","resume4")
        ,new Item(5, "Nm5","resume5"))
        
    return list;
   
  }
}