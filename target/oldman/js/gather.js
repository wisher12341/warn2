/**
 * Created by netlab606 on 2017/5/20.
 */
function formatAction(value,row,index) {
    var e="";
    for(var i=0;i<row.rooms.length;i++){
        // if((i+1)%2==0){
        //     e+='<span class="rightSpan">';
        // }else{
        //
        // }
        e+='<span class="number">'+(i+1)+'.&nbsp;</span>';
        e+='<span class="dataRE">'+'房间ID：'+row.rooms[i].rid+'&nbsp;&nbsp;&nbsp;房间名：'+row.rooms[i].roomName+'&nbsp;&nbsp;&nbsp;&nbsp;设备ID：';
        for(var j=0;j<row.equipments.length;j++) {
            if(row.rooms[i].collectId==row.equipments[j].eid) {
                e += row.equipments[j].eid + '&nbsp;&nbsp;&nbsp;设备名：' + row.equipments[j].eName + '</span>';
            }
        }
        // if((i+1)%2==0){
            e+='</span><br/>';
        // }
    }
    return e;
}
