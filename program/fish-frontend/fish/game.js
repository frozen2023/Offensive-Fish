    let canvas = document.getElementById('gameCanvas');
    let ctx = canvas.getContext('2d');
    let initscore = 0;
    //创建一个图片对象：
    let fishImg = new Image();
    fishImg.src = '微信图片_20231110164041_副本.png';
    let fishX = canvas.width / 2;
    let fishY = canvas.height / 2;
    let sx=1,sy=1,sx1,sy1,sx2,sy2,sp1,sp2;
    const fishSpeed = 30;
    //图片加载完成之后绘制图片：
    // fishImg.onload = function () {
    //     ctx.drawImage(fishImg, fishX, fishY); // 在(0,0)处绘制原图
    // }

    const collisionSound = new Audio('fi.MP3');

    // 播放碰撞音效的函数
    function playCollisionSound() {
    collisionSound.play();
    }
    let flag=1
    function playMusic() {
        var audio = document.getElementById("myAudio");
        if(flag){
        audio.play();
        flag = 0;
    }
        else{
            audio.pause(); // 暂停播放
            flag = 1;
        }
    }
    
    let fish1Img = new Image();
    fish1Img.src = 'QQ截图20231111204837.png';
    let fish1X = canvas.width;
    let fish1Y = Math.random()*canvas.height;
    // fish1Img.onload = function () {
    //     ctx.drawImage(fish1Img, fish1X, fish1Y); // 在(0,0)处绘制原图
    // }

    let fish2Img = new Image();
    fish2Img.src = 'QQ截图20231113210259.png';
    let fish2X = canvas.width;
    let fish2Y = Math.random()*canvas.height;
    // fish2Img.onload = function () {
    //     ctx.drawImage(fish2Img, fish2X, fish2Y); // 在(0,0)处绘制原图
    // }
    
    // 添加键盘事件监听器
    document.addEventListener('keydown', function (event) {
        switch (event.key) {
            case 'ArrowUp':
                fishY -= fishSpeed;
                if (fishY < 0) { // 如果鱼向上移动超出了上边界
                    fishY = 0; // 将鱼的位置设置为上边界
                }
                break;
            case 'ArrowDown':
                fishY += fishSpeed;
                if (fishY + fishImg.height+sy > canvas.height) { // 如果鱼向下移动超出了下边界
                    fishY = canvas.height - fishImg.height-sy; // 将鱼的位置设置为下边界
                }
                break;
            case 'ArrowLeft':
                fishX -= fishSpeed;
                if (fishX < 0) { // 如果鱼向左移动超出了左边界
                    fishX = 0; // 将鱼的位置设置为左边界
                }
                break;
            case 'ArrowRight':
                fishX += fishSpeed;
                if (fishX + fishImg.width+sx > canvas.width) { // 如果鱼向右移动超出了右边界
                    fishX = canvas.width - fishImg.width-sx; // 将鱼的位置设置为右边界
                }
                break;
        }
        // 重新绘制鱼的位置
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.drawImage(fishImg, fishX, fishY,fishImg.width + sx, fishImg.height + sy);
        if(fish1Img)
        ctx.drawImage(fish1Img, fish1X, fish1Y,fishImg.width + sx1, fishImg.height + sy1);
        if(fish2Img)
        ctx.drawImage(fish2Img, fish2X, fish2Y,fishImg.width + sx2, fishImg.height + sy2);
        });
    const addScore = function(x){
        const scoreDom = document.querySelector('.score')
        initscore+=x
        scoreDom.innerHTML = initscore
    }
    function isOverlap1() {
  return (
    fishX < fish1X + fish1Img.width+sx1 &&
    fishX + fishImg.width+sx1 > fish1X &&
    fishY < fish1Y + fish1Img.height+sy1 &&
    fishY + fishImg.height+sy1 > fish1Y
  );}

  function isOverlap2() {
  return (
    fishX < fish2X + fish2Img.width+sx2 &&
    fishX + fishImg.width+sx2 > fish2X &&
    fishY < fish2Y + fish2Img.height+sy2 &&
    fishY + fishImg.height+sy2 > fish2Y
  );

}

    sx1=Math.random()*sx*2;sy1=sx1;
    sx2=Math.random()*sx*2;sy2=sx2;

    let timer = null
    const hor = function() {
        
        if(timer) {
            clearInterval(timer)
        }
        timer = setInterval(()=>{
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.drawImage(fishImg, fishX, fishY,fishImg.width + sx, fishImg.height + sy);

        fish1X -= 1;
        if(fish1Img )
        {ctx.drawImage(fish1Img, fish1X, fish1Y,fishImg.width + sx1, fishImg.height + sy1);
        if(isOverlap1()){
        if(sx<=sx1)
        window.location.href = "manage.html";
        sx+=sx1;sy+=sy1;
        playCollisionSound();
        if(sx>=100)
        window.location.href = "manage.html";
        fish1Img = null;
        sx1=Math.random()*sx*2;sy1=sx1;
        addScore(sx1);
        fish1Img = new Image();
        fish1Img.src = 'QQ截图20231111204837.png';
        fish1X = canvas.width;
        fish1Y = Math.random()*canvas.height;
        }
        if(fish1X + fish1Img.width == 0 ){
        fish1Img = null;
        sx1=Math.random()*sx*2;sy1=sx1;
        fish1Img = new Image();
        fish1Img.src = 'QQ截图20231111204837.png';
        fish1X = canvas.width;
        fish1Y = Math.random()*canvas.height;
        }
        }
        
        fish2X -= 1;
        if(fish2Img)
        {
        ctx.drawImage(fish2Img, fish2X, fish2Y,fishImg.width + sx2, fishImg.height + sy2);
        if(isOverlap2()){
        if(sx<=sx2)
        window.location.href = "manage.html";
        sx+=sx2;sy+=sy2;
        playCollisionSound();
        if(sx>=100)
        window.location.href = "manage.html";
        fish2Img = null;
        sx2=Math.random()*sx*2;sy2=sx2;
        addScore(sx2);
        fish2Img = new Image();
        fish2Img.src = 'QQ截图20231113210259.png';
        fish2X = canvas.width;
        fish2Y = Math.random()*canvas.height;
        }
        if(fish2X + fish2Img.width == 0 ){
        fish2Img = null;
        sx2=Math.random()*sx*2;sy2=sx2;
        fish2Img = new Image();
        fish2Img.src = 'QQ截图20231113210259.png';
        fish2X = canvas.width;
        fish2Y = Math.random()*canvas.height;
        }
        }

        }, 10)
    }
    
    hor();
