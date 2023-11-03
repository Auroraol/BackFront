/*on:是负责往数组里安排一个队列的，程序池*/
function on(ele,type,fn){
    if(ele.addEventListener){
        ele.addEventListener(type,fn);
    }else{
        if(!ele["aEvent"+type]){
            ele["aEvent"+type]=[];
            //bind3(ele,type,run);//只会执行一次,防止重复绑定；上一次封装的事件库是用bind解决的
            ele.attachEvent("on"+type,function(){run.call(ele)})//放在这里，因为是在if里，所以是有条件执行的，而且这里是只执行一次；防止重复绑定；
        }
        var aryEvent=ele["aEvent"+type];
        for(var i=0;i<aryEvent.length;i++){//防止同一个方法被同事件绑定；
            if(aryEvent[i]==fn)return;
        }
        aryEvent.push(fn);
    }
}

/*run:负责具体的执行,在这里把IE兼容性全部解决掉;run方法是由系统的事件来触发的,真正绑定的是run方法*/
function run(){//run方法只用在IE678；
    var e=window.event;
    e.target= e.srcElement;
    e.pageX=(document.documentElement.scrollLeft||document.body.scrollLeft)+ e.clientX;
    e.pageY=(document.documentElement.scrollTop||document.body.scrollTop)+ e.clientY;
    e.stopPropagation=function(){e.cancelBubble=true;}//阻止事件传播;
    e.preventDefault=function(){e.returnValue=false;}//阻止事件默认行为;
    var a=this["aEvent"+e.type];
    for(var i=0;i< a.length;){
        /*下面是防止数组塌陷的*/
        if(typeof a[i]=="function"){//这个是和off呼应的，off里有为null的值；
            a[i].call(this,e);//以后给元素写的方法，约定好都要写一个e的参数，这样就不需要再解决IE兼容性了；
            i++;
        }else{
            a.splice(i,1);
        }
    }
}

function off(ele,type,fn){
    if(ele.removeEventListener){
        ele.removeEventListener(type,fn);
    }else {
        var aryEvent=ele["aEvent"+type];
        if(aryEvent&& aryEvent.length){
            for(var i=0;i<aryEvent.length;i++){
                if(aryEvent[i]==fn){
                    aryEvent[i]=null;
                    return;
                }
            }
        }
    }
}

function bindThis(obj,fn){
    return function(e){fn.call(obj,e)}
}