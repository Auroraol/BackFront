// JavaScript Document
<script>
var gamediv=document.getElementById("gamediv");
var mysteryhomediv=document.getElementById("mysteryhomediv");
var scorediv=document.getElementById("scorediv");
var scorelabel=document.getElementById("initialscore");
var parsediv=document.getElementById("parsediv");
var enddiv=document.getElementById("enddiv");
var mysteryplanscoretext=document.getElementById("mysteryplanscoretext");
var scores=0;
function mysteryplan(hp,x,y,sizex,sizey,score,endtime,speed,trunkimage,imageurl)
{
	this.mysteryplanx=x;
	this.mysteryplany=y;
	this.mysteryimagenode=null;
	this.mysteryhp=hp;
	this.mysteryplanscore=score;
	this.mysteryplansizex=sizex;
	this.mysteryplansizey=sizey;
	this.mysteryplantrunkimage=trunkimage;
	this.mysteryplanisdie=false;
	this.mysteryplanendtime=endtime;
	this.mysteryplanendtimes=0;
	this.mysteryplanspeed=speed;
	this.mysteryplanmove=function(){
		if(scores<=5000)
		{
			this.mysteryimagenode.style.top=this.mysteryimagenode.offsetTop+this.mysteryplanspeed+"px";
		}
		if(scores>5000&&scores<=10000)
		{
			this.mysteryimagenode.style.top=this.mysteryimagenode.offsetTop+this.mysteryplanspeed+1+"px";
		}
		if(scores>10000&&scores<=15000)
		{
			this.mysteryimagenode.style.top=this.mysteryimagenode.offsetTop+this.mysteryplanspeed+2+"px";
		}
		if(scores>15000&&scores<=20000)
		{
			this.mysteryimagenode.style.top=this.mysteryimagenode.offsetTop+this.mysteryplanspeed+3+"px";
		}
		if(scores>20000&&scores<=25000)
		{
			this.mysteryimagenode.style.top=this.mysteryimagenode.offsetTop+this.mysteryplanspeed+4+"px";
		}
		if(scores>25000&&scores<=30000)
		{
			this.mysteryimagenode.style.top=this.mysteryimagenode.offsetTop+this.mysteryplanspeed+5+"px";
		}
		else
		{
			this.mysteryimagenode.style.top=this.mysteryimagenode.offsetTop+this.mysteryplanspeed+6+"px";
		}
	}
	this.init=function()
	{
		this.mysteryimagenode=document.createElement("img");
		this.mysteryimagenode.style.left=this.mysteryplanx+"px";
		this.mysteryimagenode.style.top=this.mysteryplany+"px";
		this.mysteryimagenode.src=imageurl;
		gamediv.appendChild(this.mysteryimagenode);
	}
	this.init();
}

function mysterybullet(x,y,sizex,sizey,imageurl)
{
	this.mysterybulletx=x;
	this.mysyerybullety=y;
	this.mysterybulletimage=null;
	this.mysterybulletsizex=sizex;
	this.mysterybulletsizey=sizey;
	this.mysterybulletattach=1;
	this.mysterybulletmove=function()
	{
		this.mysterybulletimage.style.top=this.mysterybulletimage.style.offsetTop-20+"px";
	}
	this.init=function(){
		this.mysterybulletimage=document.createElement("img");
		this.mysterybulletimage.style.left=this.mysterybulletx+"px";
		this.mysterybulletimage.style.top=this.mysterybullety+"px";
		this.mysterybulletimage.src=imageurl;
		gamediv.appendChild(this.mysterybulletimage);
	}
	this.init();
}

function mysteryoddbullet(x,y)
{
	mysterybullet.call(this,x,y,6,14,"../image/zidan.jpg");
}

function mysteryenemyplan(hp,a,b,sizex,sizey,score,endtime,speed,trunkimage,imageurl)
{
	mysteryplan.call(this,hp,random(a,b),-100,sizex,sizey,score,endtime,speed,trunlimage,imageurl);
}

function random(a,b)
{
	return Math.floor(a+Math.random()*(b-a));
}

function mysteryourplan(x,y)
{
	var imageurl="../image/air.jpg";
	mysteryplan.call(this,1,x,y,66,80,0,660,0,"../image/baozha.jpg",imageurl);
	this.mysteryimagenode.setAttribute('id','mysteryourplan');
}

var mysteryselfplan=new mysteryourplan(120,485);

var mysteryourplan=document.getElementById('mysteryourplan');

var mysteryshift=function(){
	var mysteryevent=window.event||arguments[0];
	var mysterystart=mysteryevent.srcElement||mysteryevent.target;
	var selfmysteryplanx=mysteryevent.clientX-500;
	var selfmysteryplany=mysteryevent.clientY;
	mysteryourplan.style.left=selfmysteryplanx-mysteryselfplan.mysteryplansizex/2+"px";
	mysteryourplan.style.top=selfmysteryplany-mysteryselfplan.mysteryplansizey/2+"px";
}

var mysterynumber=0;
var mysterysuspend=function(){
	if(mysterynumber==0)
	{
		pausediv.style.display=block;
		if(document.removeEventListener)
		{
			gamediv.removeEventListener("mousemove",mysteryshift,true);
			mysterybodyobj.removeEventListener("mouseover",mysteryboundary,true);
		}
		else if(document.detachEvent)
		{
			gamediv.detachEvent("onmousemove",mysteryshift);
			mysterybodyobj.detachEvent("onmouseover",mysteryboundary);
		}
		clearInterval(set);
		mysterynumber=1;
	}
	else
	{
		pausediv.style.display=none;
		if(document.addEventListener)
		{
			gamediv.addEventListener("mousemove",mysteryshift,true);
			mysterybodyobj.addEventListener("mouseover",mysteryboundary,true);
		}
		else if(document.attachEvent)
		{
			gamediv.attachEvent("onmousemove",mysteryshift);
			mysterybodyobj.attachEvent("onmouseover",mysteryboundary);
		}
		set=setInterval(begingame,20);
		mysterynumber=0;
	}
}

var mysteryboundary=function()
{
	var mysteryevent=window.event||arguments[0];
	var selfmysteryplanx=mysteryevent.clientX;
	var selfmysteryplany=mysteryevent.clientY;
	if(selfmysteryplanx<505||selfmysteryplanx>815||selfmysteryplany<0||selfmysteryplany>568)
	{
		if(document.removeEventListener)
		{
			gamediv.removeEventListener("mousemove",mysteryshift,true);=
		}
		else if(document.detachEvent)
		{
			gamediv.detachEvent("onmousemove",mysteryshift);
		}
	}
	else
	{
		if(document.addEventListener)
		{
			gamediv.addEventListener("mousemove",mysteryshift,true);
		}
		else if(document.attachEvent)
		{
			gamediv.attachEvent("onmousemove",mysteryshift);
		}
	}
}

var mysterybodyobj=document.getElementsByTagName("body")[0];
if(document.addEventListener)
{
	gamediv.addEventListener("mousemove",mysteryshift,true);
	mysterybodyobj.addEventListener("mouseover",mysteryboundary,true);
	mysteryselfplan.mysteryimagenode.addEventListener("click",mysterysuspend,true);
	pausediv.getEventsByTagName("button")[0].addEventListener("click",mysterysuspend,true);
	pausediv.getEventsByTagName("button")[1].addEventListener("click",tryagain,true);
}
else if(document.attachEvent)
{
	gamediv.attachEvent("onmousemove",mysteryshift);
	mysterybodyobj.attachEvent("onmouseover",mysteryboundary);
	mysteryselfplan.mysteryimagenode.attachEvent("onclick",mysterysuspend);
	pausediv.getEventsByTagName("button")[0].attachEvent("onclick",mysterysuspend);
	pausediv.getEventsByTagName("button")[1].attachEvent("onclick",tryagain);
}
mysteryselfplan.mysteryimagenode.style.display="none";
var mysteryenemyplans=[];
var mysterybullets=[];
var mark=0;
var mark1=0;
var position=0;
function begingame
{
	gamediv.style.backgroundPosition=position+"px";
	position+=0.5;
	if(position==568)
	{
		position=0;
	}
	mark++;
	if(mark==20)
	{
		mark1++;
		if(mark1%5==0)
		{
			mysteryenemyplans.push(new mysteryenemyplan(6,25,264,46,60,5000,360,random(1,3),"../image/baozha.jpg","../image/baozha.jpg"));
		}
		if(mark1==20)
		{
			mysteryenemyplans.push(new mysteryenemyplan(12,57,210,110,164,30000,540,1,"../image/baozha.jpg","../image/baozha.jpg"));
		}
		else
		{
			mysteryenemyplans.push(new mysteryenemyplan(1,19,286,34,24,1000,360,random(1,4),"../image/baozha.jpg","../image/baozha.jpg"));
		}
		mark=0;
	}
	var mysteryenemyplanslen=mysteryenemyplans.length;
	for(var i=0;i<mysteryenemyplanslen;i++)
	{
		if(mysteryenemyplans[i].mysteryplanisdie!=true)
		{
			mysteryenemyplans[i].mysteryplanmove();
		}
		if(mysteryenemyplans[i].mysteryimagenode.offsetTop>568)
		{
			gameDiv.removechild(mysteryenemyplans[i].mysteryimagenode);
			mysteryenemyplans.splice(i,1);
			mysteryenemyplanslen--;
		}
		if(mysteryenemyplans[i].mysteryplanisdie==true)
		{
			mysteryenemyplans[i].mysteryplanendtimes+=20;
			if(mysteryenemyplans[i].mysteryplanendtimes==mysteryenemyplans[i].mysteryplanendtime)
			{
				gameDiv.removechild(mysteryenemyplans[i].mysteryimagenode);
			    mysteryenemyplans.splice(i,1);
			    mysteryenemyplanslen--;
			}
		}
	}
	if(mark%5==0)
	{
		mysterybullets.push(new mysteryoddbullet(parseInt(mysteryselfplan.mysteryimagenode.style.left)+31,parseInt(mysteryselfplan.mysteryimagenode.style.top)-10);
	}
	var mysterybulletslen=mysterybullets.length;
	for(var i=0;i<mysterybulletslen;i++)
	{
		mysterybullets[i].mysterybulletmove();
		if(mysterybullets[i].mysterybulletimage.offsetTop<0)
		{
			gameDiv.removechild(mysterybullets[i].mysterybulletimage);
			mysterybullets.splice(i,1);
			mysterybulletslen-;
		}
	}
	for(var k=0;k<mysterybulletslen;k++)
	{
		for(var j=0;j<mysteryenemyplanslen;j++)
		{
			if(mysteryenemyplans[j].mysteryimagenode.offsetLeft+mysteryenemyplans[j].mysteryplansizex>=mysteryselfplan.mysteryimagenode.offsetLeft
			&&mysteryenemyplans[j].mysteryimagenode.offsetLeft<=mysteryselfplan.mysteryimagenode.offsetLeft+mysteryselfplan.mysteryplansizex)
			{
				if(mysteryenemyplans[j].mysteryimagenode.offsetTop+mysteryenemyplans[j].mysteryplansizey>=mysteryselfplan.mysteryimagenode.offsetTop
			    &&mysteryenemyplans[j].mysteryimagenode.offsetTop<=mysteryselfplan.mysteryimagenode.offsetTop+mysteryselfplan.mysteryplansizey)
				{
					mysteryselfplan.mysteryimagenode.src="../image/success.jpg";
					enddiv.style.display="block";
					mysteryplaanscoretext.innerhtml=scores;
					if(document.removeEventListener)
	            	{
			            gamediv.removeEventListener("mousemove",mysteryshift,true);
			            mysterybodyobj.removeEventListener("mouseover",mysteryboundary,true);
		            }
		            else if(document.detachEvent)
		            {
			            gamediv.detachEvent("onmousemove",mysteryshift);
			            mysterybodyobj.detachEvent("onmouseover",mysteryboundary);
		            }
					clearInterval(set);
				}
			}
			if(mysterybullets[k].mysterybulletimage.offsetLeft+mysterybullets[k].mysterybulletsizex>mysteryenemyplans[j].mysteryimagenode.offsetLeft
			&&mysterybullets[k].mysterybulletimage.offsetLeft<mysteryenemyplans[j].mysteryimagenode.offsetLeft+mysteryselfplan.mysteryplansizex)
			{
				if(mysterybullets[k].mysterybulletimage.offsetTop+mysterybullets[k].mysterybulletsizey>mysteryenemyplans[j].mysteryimagenode.offsetTop
			&&mysterybullets[k].mysterybulletimage.offsetTop<mysteryenemyplans[j].mysteryimagenode.offsetTop+mysteryselfplan.mysteryplansizey)
				{
					mysteryenemyplans[j].mysteryplanhp=mysteryenemyplans[j].mysteryplanhp-mysterybullets[k].mysterybulletattach;
					if(mysteryenemyplans[j].mysteryplanhp=0)
					{
						scores=scores+mysteryenemyplans[j].mysteryplanscoretext;;
						scorelabel.innerHTML=scores;
						mysteryenemyplans[j].mysteryimagenode.src=mysteryenemyplans[j].mysteryplantrunkimage;
						mysteryenemyplans[j].mysteryplanisdie=true;
					}
					gamediv.removeChild(mysterybullets[k].mysterybulletimage);
					mysteryBullets.splice(k,1);
					mysterybulletslen--;
					break;
				}
			}
		}
	}
}
var set;
function startgame()
{
	mysteryhomediv.style.display="none";
	gamediv.style.display="block";
	mysteryselfplan.mysteryiamgenode.style.display="block";
	scorediv.style.display="block";
	set=setInterval(begingame,20);
}
function tryagain()
{
	location.reload(true);
}
</script>		
			