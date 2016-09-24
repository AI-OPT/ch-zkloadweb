/*$(document).ready(function(){
$(".logo").click(function () {
   $(".subnav").animate({height:'toggle'},100);
   $(".wrapper").toggleClass("wrappera");
});
});*/


$(function(){
$(".nav li").click(function () {
                $(".nav li").each(function () {
                    $(this).removeClass("current");
                });
                $(this).addClass("current");
            });
$('.nav li').click(function(){
 $(this).children('.current-cnt').show().end().siblings(this).children(".current-cnt").hide();
});
 $('.current-cnt span').click(function(){
 $(this).children('.current-cnt1').show().end().siblings(this).children(".current-cnt1").hide();
 });
});

 $(document).ready(function() {
 $("ul.nav li").hover(function(){
  $(this).addClass("on");
 
 },
 function(){
  $(this).removeClass("on");
 
 })
 });
 
 $(document).ready(function() {
 $("ul.nav li").hover(function(){
  $(this).parent("ul").siblings("h3").addClass("choice");
 
 },
 function(){
  $(this).parent("ul").siblings("h3").removeClass("choice");
 })
 });
 
 $(document).ready(function() { 
  if ($("ul.nav li").find("ul") .html()!="") {
  $("ul.nav li").parent("ul").siblings("h3").append("<span class='sub'></span>");  
  }
 });


$(function(){ 
$(document).bind("click",function(e){ 
var target = $(e.target); 
if(target.closest(".nav li").length == 0){ 
$(".current-cnt").hide(); } 
});
});


$(document).ready(function(){
$('.user-cnt p').click(function(){
   $(this).css("background","#f1f1f1"); 
   $(".user-cnt ul").animate({height:'toggle'},80);
   $(".user-cnt p span").css("color","#043a86");
});
});


$(function(){ 
$(document).bind("click",function(e){ 
var target = $(e.target); 
if(target.closest(".user-cnt p").length == 0){ 
$(".user-cnt ul").hide();
$(".user-cnt p span").css("color","#fff"); } 
});
});


$(function(){ 
$(document).bind("click",function(e){ 
var target = $(e.target); 
if(target.closest(".user-cnt p").length == 0){ 
$(".user-cnt p").css("background","none");
$(".user-cnt p span").css("color","#fff");  } 
});
});


$(function(){
$(".msg").click(function () {
   $(".msg-cnt").animate({right:'0'},200);
});
});


$(function(){
$(".p a").click(function () {
   $(".msg-cnt").animate({right:'-280px'},200);
});
});

