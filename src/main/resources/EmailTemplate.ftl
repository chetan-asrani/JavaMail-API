<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html charset=UTF-8" />
    <title>Background</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        .container {
            width: 50vw;
            height: 50vh;
            display: grid;
            grid-template-columns: repeat(13,1fr);
            grid-template-rows: repeat(11, 1fr);
        }
        .card {
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            background: bisque;
            transition: 0.3s;
            border-radius: 10px;
            grid-column: 5/10;
            grid-row: 4/9;
        }
        .card:hover {
            box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
        }      
        h1 {
            text-align: center;
            font-family:'Segoe UI';
            font-weight: 900;
            font-size: 3.4vw;
            margin-top: 13vh;
            margin-bottom: 3vh;
            color:blueviolet
        }
        h3 {
            text-align: center;
            font-family:'Segoe UI';
            font-weight: 900;
            font-size: 3vw;
            color:blueviolet
        }

    </style>
</head>
<body>
    <div class="container">
        <div class="card">    
	        <h1>Congratulations ${name}</h1>
	        <h3>Happy Birthday!!</h3>    
        </div>
    </div>
</body>
</html>