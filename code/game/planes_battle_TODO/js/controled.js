
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
	this.mysteryplanscoretext=score;
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
		else if(scores>5000&&scores<=10000)
		{
			this.mysteryimagenode.style.top=this.mysteryimagenode.offsetTop+this.mysteryplanspeed+1+"px";
		}
		else if(scores>10000&&scores<=15000)
		{
			this.mysteryimagenode.style.top=this.mysteryimagenode.offsetTop+this.mysteryplanspeed+2+"px";
		}
		else if(scores>15000&&scores<=20000)
		{
			this.mysteryimagenode.style.top=this.mysteryimagenode.offsetTop+this.mysteryplanspeed+3+"px";
		}
		else if(scores>20000&&scores<=25000)
		{
			this.mysteryimagenode.style.top=this.mysteryimagenode.offsetTop+this.mysteryplanspeed+4+"px";
		}
		else if(scores>25000&&scores<=30000)
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
		this.mysterybulletimage.style.top=this.mysterybulletimage.offsetTop+20+"px";
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
	mysterybullet.call(this,x,y,6,14,"../image/zidan.png");
}

function mysteryenemyplan(hp,a,b,sizex,sizey,score,endtime,speed,trunkimage,imageurl)
{
	mysteryplan.call(this,hp,random(a,b),-100,sizex,sizey,score,endtime,speed,trunkimage,imageurl);
}

function random(a,b)
{
	return Math.floor(a+Math.random()*(b-a));
}

function mysteryourplan(x,y)
{
	var imageurl="../image/air.png";
	mysteryplan.call(this,1,x,y,66,80,0,660,0,"../image/feiji.png",imageurl);
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
		pausediv.style.display="block";
		if(document.removeEventListener)
		{
			gamediv.removeEventListener("mousemove",mysteryshift,true);
			mysterybodyobj.removeEventListener("mousemove",mysteryboundary,true);
		}
		else if(document.detachEvent)
		{
			gamediv.detachEvent("onmousemove",mysteryshift);
			mysterybodyobj.detachEvent("onmousemove",mysteryboundary);
		}
		clearInterval(set);
		mysterynumber=1;
	}
	else
	{
		pausediv.style.display="none";
		if(document.addEventListener)
		{
			gamediv.addEventListener("mousemove",mysteryshift,true);
			mysterybodyobj.addEventListener("mousemove",mysteryboundary,true);
		}
		else if(document.attachEvent)
		{
			gamediv.attachEvent("onmousemove",mysteryshift);
			mysterybodyobj.attachEvent("onmousemove",mysteryboundary);
		}
		set=setInterval(begingame,20);
		mysterynumber=0;
	}
}

var mysteryboundary=function()
{
	var mysteryevent=window.event||arguments[0];
	var mysterystart=mysteryevent.srcElement||mysteryevent.target;
	var selfmysteryplanx=mysteryevent.clientX;
	var selfmysteryplany=mysteryevent.clientY;
	if(selfmysteryplanx<510||selfmysteryplanx>805||selfmysteryplany<0||selfmysteryplany>550)
	{
		if(document.removeEventListener)
		{
			gamediv.removeEventListener("mousemove",mysteryshift,true);
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
	mysteryselfplan.mysteryimagenode.addEventListener("click",mysterysuspend,true);
	mysterybodyobj.addEventListener("mousemove",mysteryboundary,true);
	pausediv.getElementsByTagName("button")[0].addEventListener("click",mysterysuspend,true);
	pausediv.getElementsByTagName("button")[1].addEventListener("click",tryagain,true);
}
else if(document.attachEvent)
{
	gamediv.attachEvent("onmousemove",mysteryshift);
	mysteryselfplan.mysteryimagenode.attachEvent("onclick",mysterysuspend);
	mysterybodyobj.attachEvent("onmousemove",mysteryboundary);
	pausediv.getElementsByTagName("button")[0].attachEvent("onclick",mysterysuspend);
	pausediv.getElementsByTagName("button")[1].attachEvent("click",tryagain,true);
}
mysteryselfplan.mysteryimagenode.style.display="none";
var mysteryenemyplans=[];
var mysterybullets=[];
var mark=0;
var mark1=0;
var position=0;
function begingame()
{
	audio(0);
	audio(2);
	gamediv.style.backgroundPositionY=position+"px";
	position+=0.5;
	if(position==568)
	{
		position=0;
	}
	mark++;
	if(mark==50)
	{
		mark1++;
		if(mark1%10==0)
		{
			mysteryenemyplans.push(new mysteryenemyplan(6,25,264,46,60,5000,360,random(1,3),"../image/blast2.png","../image/my33.png"));
		}
		if(mark1==50)
		{
			mysteryenemyplans.push(new mysteryenemyplan(12,57,210,110,164,30000,540,1,"../image/blast3.gif","../image/my111.png"));
			mark1=0;
		}
		if(mark1%5==0)
		{
			mysteryenemyplans.push(new mysteryenemyplan(1,19,286,34,24,1000,360,random(1,4),"../image/blast1.png","../image/my4.png"));
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
		else
		{
			mysteryenemyplans[i].mysteryplanendtimes+=20;
			if(mysteryenemyplans[i].mysteryplanendtimes==mysteryenemyplans[i].mysteryplanendtime)
			{
				gamediv.removeChild(mysteryenemyplans[i].mysteryimagenode);
			    mysteryenemyplans.splice(i,1);
			    mysteryenemyplanslen--;
				continue;
			}
		}
		if(mysteryenemyplans[i].mysteryimagenode!=null&&mysteryenemyplans[i].mysteryimagenode.offsetTop>550)
		{
			gamediv.removeChild(mysteryenemyplans[i].mysteryimagenode);
			mysteryenemyplans.splice(i,1);
			mysteryenemyplanslen--;
		}
	}
	if(mark%15==0)
	{
		var x1=mysteryselfplan.mysteryimagenode.offsetLeft;
		var y1=mysteryselfplan.mysteryimagenode.offsetTop;
		mysterybullets.push(new mysteryoddbullet(parseInt(x1)+40,parseInt(y1)-10));
	}
	var mysterybulletslen=mysterybullets.length;
	for(var k=0;k<mysterybulletslen;k++)
	{
		mysterybullets[k].mysterybulletmove();
		if(mysterybullets[k].mysterybulletimage.offsetTop>565)
		{
			gamediv.removeChild(mysterybullets[k].mysterybulletimage);
			mysterybullets.splice(k,1);
			mysterybulletslen--;
		}
	}
	audio(0);
	audio(2);
	for(var k=0;k<mysterybulletslen;k++)
	{
		for(var j=0;j<mysteryenemyplanslen;j++)
		{
			if(mysteryenemyplans[j].mysteryplanisdie==false){
			if((mysteryenemyplans[j].mysteryimagenode.offsetLeft+mysteryenemyplans[j].mysteryplansizex>=mysteryselfplan.mysteryimagenode.offsetLeft)
			&&(mysteryenemyplans[j].mysteryimagenode.offsetLeft<=mysteryselfplan.mysteryimagenode.offsetLeft+mysteryselfplan.mysteryplansizex))
			{
				if((mysteryenemyplans[j].mysteryimagenode.offsetTop+mysteryenemyplans[j].mysteryplansizey>=mysteryselfplan.mysteryimagenode.offsetTop+40)
			    &&(mysteryenemyplans[j].mysteryimagenode.offsetTop<=mysteryselfplan.mysteryimagenode.offsetTop+mysteryselfplan.mysteryplansizey-20))
				{
					mysteryselfplan.mysteryimagenode.src="../image/blast2.png";
					enddiv.style.display="block";
					mysteryplanscoretext.innerHTML=scores;
					if(document.removeEventListener)
	            	{
			            gamediv.removeEventListener("mousemove",mysteryshift,true);
			            mysterybodyobj.removeEventListener("mousemove",mysteryboundary,true);
		            }
		            else if(document.detachEvent)
		            {
			            gamediv.detachEvent("onmousemove",mysteryshift);
			            mysterybodyobj.detachEvent("onmousemove",mysteryboundary);
		            }
					clearInterval(set);
				}
			}
			if((mysterybullets[k].mysterybulletimage.offsetLeft+mysterybullets[k].mysterybulletsizex>mysteryenemyplans[j].mysteryimagenode.offsetLeft)
			&&(mysterybullets[k].mysterybulletimage.offsetLeft<mysteryenemyplans[j].mysteryimagenode.offsetLeft+mysteryenemyplans[j].mysteryplansizex))
			{
				if((mysterybullets[k].mysterybulletimage.offsetTop+mysterybullets[k].mysterybulletsizey>=mysteryenemyplans[j].mysteryimagenode.offsetTop)
			&&(mysterybullets[k].mysterybulletimage.offsetTop<=mysteryenemyplans[j].mysteryimagenode.offsetTop+mysteryenemyplans[j].mysteryplansizey))
				{
					audio(1);
					mysteryenemyplans[j].mysteryhp=mysteryenemyplans[j].mysteryhp-mysterybullets[k].mysterybulletattach;
					if(mysteryenemyplans[j].mysteryhp==0)
					{
						scores=scores+mysteryenemyplans[j].mysteryplanscoretext;;
						scorelabel.innerHTML=scores;
						mysteryenemyplans[j].mysteryimagenode.src=mysteryenemyplans[j].mysteryplantrunkimage;
						mysteryenemyplans[j].mysteryplanisdie=true;
					}
					gamediv.removeChild(mysterybullets[k].mysterybulletimage);
					mysterybullets.splice(k,1);
					mysterybulletslen--;
					break;
					}
				}
			}
		}
	}
}
function audio(n){
	var music = ["audio/bgm.mp3","audio/boom.mp3","audio/bullet.mp3"],
		dio = document.createElement("audio");
	if(n != 1){
		dio.className = "dio"
	}
	dio.src = music[n];
	dio.autoplay = "autoplay";
	dio.loop = "loop";
	gamediv.appendChild(dio);
	if (n==1) {
		setTimeout(function(){
			gamediv.removeChild(dio);
		},800)
	}
}
var set;

$('#game').click(function (){
	mysteryhomediv.style.display="none";
	gamediv.style.display="block";
	mysteryselfplan.mysteryimagenode.style.display="block";
	scorediv.style.display="block";
	set=setInterval(begingame,20);
})
function tryagain()
{
	location.reload(true);
}