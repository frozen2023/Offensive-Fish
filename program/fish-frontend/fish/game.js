    let canvas = document.getElementById('gameCanvas');
    let ctx = canvas.getContext('2d');
    let initscore = 0;
    //创建一个图片对象：
    let fishImg = new Image();
    fishImg.src = '微信图片_20231110164041_副本.png';
    let fishX = canvas.width / 2;
    let fishY = canvas.height / 2;
    let sx=0,sy=0;
    const fishSpeed = 30;
    //图片加载完成之后绘制图片：
    fishImg.onload = function () {
        ctx.drawImage(fishImg, fishX, fishY); // 在(0,0)处绘制原图
    }

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
    fish1Img.onload = function () {
        ctx.drawImage(fish1Img, fish1X, fish1Y); // 在(0,0)处绘制原图
    }

    let fish2Img = new Image();
    fish2Img.src = 'QQ截图20231113210259.png';
    let fish2X = canvas.width;
    let fish2Y = Math.random()*canvas.height;
    fish2Img.onload = function () {
        ctx.drawImage(fish2Img, fish2X, fish2Y); // 在(0,0)处绘制原图
    }
    
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
                if (fishY + fishImg.height > canvas.height) { // 如果鱼向下移动超出了下边界
                    fishY = canvas.height - fishImg.height; // 将鱼的位置设置为下边界
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
                if (fishX + fishImg.width > canvas.width) { // 如果鱼向右移动超出了右边界
                    fishX = canvas.width - fishImg.width; // 将鱼的位置设置为右边界
                }
                break;
        }
        // 重新绘制鱼的位置
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.drawImage(fishImg, fishX, fishY,fishImg.width + sx, fishImg.height + sy);
        if(fish1Img)
        ctx.drawImage(fish1Img, fish1X, fish1Y);
        if(fish2Img)
        ctx.drawImage(fish2Img, fish2X, fish2Y);
        });
    const addScore = function(){
        const scoreDom = document.querySelector('.score')
        initscore++
        scoreDom.innerHTML = initscore
    }
    function isOverlap1() {
  return (
    fishX < fish1X + fish1Img.width &&
    fishX + fishImg.width > fish1X &&
    fishY < fish1Y + fish1Img.height &&
    fishY + fishImg.height > fish1Y
  );}

  function isOverlap2() {
  return (
    fishX < fish2X + fish2Img.width &&
    fishX + fishImg.width > fish2X &&
    fishY < fish2Y + fish2Img.height &&
    fishY + fishImg.height > fish2Y
  );

}
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
        {ctx.drawImage(fish1Img, fish1X, fish1Y);
        if(isOverlap1()){
        sx++;sy++;
        playCollisionSound();
        fish1Img = null;
        addScore();
        fish1Img = new Image();
        fish1Img.src = 'QQ截图20231111204837.png';
         fish1X = canvas.width;
        fish1Y = Math.random()*canvas.height;
        }
        if(fish1X + fish1Img.width == 0 ){
        fish1Img = null;
        fish1Img = new Image();
        fish1Img.src = 'QQ截图20231111204837.png';
         fish1X = canvas.width;
        fish1Y = Math.random()*canvas.height;
        }
        }
        
        fish2X -= 1;
        if(fish2Img )
        {ctx.drawImage(fish2Img, fish2X, fish2Y);
        if(isOverlap2()){
        sx++;sy++;
        playCollisionSound();
        fish2Img = null;
        addScore();
        fish2Img = new Image();
        fish2Img.src = 'QQ截图20231113210259.png';
        fish2X = canvas.width;
        fish2Y = Math.random()*canvas.height;
        }
        if(fish2X + fish2Img.width == 0 ){
        fish2Img = null;
        fish2Img = new Image();
        fish2Img.src = 'QQ截图20231113210259.png';
        fish2X = canvas.width;
        fish2Y = Math.random()*canvas.height;
        }
        }

        }, 10)
    }
    
    hor();