
for (let btn of document.querySelectorAll(".show-btn")){
    btn.onclick = (()=>{
       let parentElement=btn.parentElement
       const passField = parentElement.querySelector("input");
       const showBtn = parentElement.querySelector("span i");
     if(passField.type === "password"){
       passField.type = "text";
       showBtn.classList.add("hide-btn");
     }else{
       passField.type = "password";
       showBtn.classList.remove("hide-btn");
     }
    });

}