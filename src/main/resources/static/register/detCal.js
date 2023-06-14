let detNum;
class Determination {
    constructor(deter) {
        this.deter=deter;
        this.detNum=deter.length;
        this.value=0;
    }
    lowerDet(lowerNeed,row){
        let result=[];
        for (let i = 0; i < lowerNeed.length-1; ++i) {
            result.push([]);
        }
        for (let i=1;i<lowerNeed.length;i++){
            let arry1=lowerNeed[i];
            for (let i2=0;i2<arry1.length;i2++){
                if (i2!==row){
                    result[i-1].push(arry1[i2]);
                }
            }
        }
        return result;

    }
    calculate(calDeter=this.deter){
        let result=0;
        switch (calDeter.length) {
            case 1:
                result=calDeter[0][0];
                break;
            case 2:
                result = calDeter[0][0] * calDeter[1][1] - calDeter[0][1] * calDeter[1][0];break;
            default:
                for (let i = 0; i < detNum; ++i) {
                result+=calDeter[0][i]* Math.pow(-1,i)* this.calculate(this.lowerDet(calDeter,i));
            }break;
        }
        return result;
    }
}
window.onload=function () {
    let detNumInput=document.getElementById("detInput");let detInOkBtn=document.getElementById("detInputOk");
    let detinputLine=document.getElementById("detInputLine");
    function insertDetLine() {
        document.getElementById("loading").innerHTML="<p>loading<p/>";
        detNum=detNumInput.value;
        document.getElementById("needClear").innerHTML="";
        for (let i = 0; i < detNum; i++) {
            let inputLine=document.createElement("input");
            inputLine.className="detLine";
            inputLine.type="text";
            detinputLine.appendChild(inputLine);
            detinputLine.appendChild(document.createElement("br"));
        }
        document.getElementById("loading").innerHTML="";
        let calBtn=document.createElement("input");
        calBtn.type="button";
        calBtn.id="calBtn";
        calBtn.value="計算";
        calBtn.onclick=function (){
            calStart();
        }
        detinputLine.appendChild(calBtn);
    }
    function calStart() {
        console.log("開始計算")
        let result=[]
        let Lins=document.getElementsByClassName("detLine");
        for (let i=0;i<Lins.length;i++){
            let lin=Lins[i];
            let a=lin.value.split(",");
            result.push(a);
        }
        let detmi=new Determination(result);
        let r=detmi.calculate()
        let d=document.createElement("span")
        d.innerText="result:"+r;
        detinputLine.appendChild(d);
        detinputLine.appendChild(document.createElement("br"))
    }
    detInOkBtn.onclick=function (){
        insertDetLine();
    };

}

