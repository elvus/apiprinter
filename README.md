# Api Printer JasperSoft with Spring Boot
A simple api to send jasperreports to the printer
## How to Use
Rename the config.properties.expample to config.properties.

Edit the file and specify the parameters
```
url=localhost //Your database url
port=3306 //Your database port
database=db //database name
user=root
pass=admin
reportFolder=reports //The reports Folder
imageFolder=reports //Report Images folder
```
Run the project

Go to http://localhost:5000/printer to list your local printers

Send a post json same as
```
$.ajax({
  url:"http://localhost:5000/printer",
  type:"post",
  contentType: "application/json; charset=utf-8",
  dataType: "json",
  data:JSON.stringify({
    id_usuario: 1, 
    subreport: "subreportname.jasper", 
    report:"reportort.jasper",
	  printer:"printername"
  }),
  success:function(data){
    data
  }
});
```
Enjoy your impress ☺
