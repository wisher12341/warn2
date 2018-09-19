/**
 * Created by netlab606 on 2017/6/7.
 */
function formatAction(value,row,index){
    var reg1=new RegExp('roomName','g');
    var reg2=new RegExp('sensorPointID','g');
    var reg3=new RegExp('roomConnected','g');
    // var reg4=new RegExp('\[','g');
    var reg5=new RegExp('\"','g');
    // var data=row.roomInfoArray.replace(reg1,'房间名').replace(reg2,'设备ID').replace(reg3,'相邻房间').replace(reg4,"").replace(reg5,"");
    var data=row.roomInfoArray.replace(reg1,'房间名').replace(reg2,'设备ID').replace(reg3,'相邻房间').replace(reg5,"");
    var dataArray=data.split('}');
    var content="";
    for(var i=0;i<dataArray.length;i++){
        if(i==0){
            dataArray[i]=dataArray[i].substring(2);
        }else{
            dataArray[i]=dataArray[i].substring(4);
        }
        content+=dataArray[i]+"<br/>";
    }
    content=content.substring(1,content.length-6);
    return content;
}