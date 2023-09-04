let userEmail = document.querySelector('.email')
let parol = document.querySelector('.parol');
let btn = document.querySelector('button')
let PostC = document.querySelector('.get')
let getC = document.querySelector('.getC')


 function auth() {
    console.log("auth post")
    let data = {
        email: userEmail.value,
        password: parol.value
    }
 fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json",
        }
    }).then(res=>res.json()).then(res=>console.log(res))
}


// get auth
btn.addEventListener('click', (e) => {
    console.log("salom")
    e.preventDefault()
    auth()
})

// get auth end



// postC star

PostC.addEventListener('click',(e)=>{
    e.preventDefault()
    postP()
})


function postP(){
    console.log("post C")
     fetch('http://localhost:8080/admin/category/add',{
         method:'POST',
         body:JSON.stringify({image:"rasm"}),
         headers: {
             "Content-Type": "application/json",
             "Authorization":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2OTM4MzQwMzUsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIiwicm9sZSI6IlJPTEVfQURNSU4iLCJleHAiOjE2OTM5MjA0MzUsImlzcyI6Ikt1bnV6IHRlc3QgcG9ydGFsaSJ9.PnmT6gEN0CJR9mUaBHMsIIMpPZWiYxSHMdFZUi9MbyZi4LPGBJmRxCA9cECupLCM-08Hgc-sX8dewmPMI3QaGw\n"
         }
     }).then(res=>res.json()).then(res=>console.log(res))
}

// postC end

// getC start


getC.addEventListener('click',(e)=>{
    e.preventDefault()
    getCC()
})



 function  getCC(){
    console.log('get category')

     fetch('http://localhost:8080/admin/category/2',{
        method:"GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2OTM4MzQwMzUsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIiwicm9sZSI6IlJPTEVfQURNSU4iLCJleHAiOjE2OTM5MjA0MzUsImlzcyI6Ikt1bnV6IHRlc3QgcG9ydGFsaSJ9.PnmT6gEN0CJR9mUaBHMsIIMpPZWiYxSHMdFZUi9MbyZi4LPGBJmRxCA9cECupLCM-08Hgc-sX8dewmPMI3QaGw\n"
        }
    }).then(res=>res.json()).then(res=>console.log(res))

}