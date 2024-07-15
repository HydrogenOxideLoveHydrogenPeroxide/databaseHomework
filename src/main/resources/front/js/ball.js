window.onload = function () {
    var MoveDom = document.getElementById('neko')
    //初始位置配置
    MoveDom.style.transform='translateX(50%)'
    MoveDom.style.left=document.body.scrollWidth-parseFloat(getComputedStyle(MoveDom, null)['width'].split('px')[0])+'px'
    MoveDom.style.top=document.body.scrollWidth/2+'px'
    // 防抖
    function debounce(fn,wait){
        var timer = null;
        return function(){
            if(timer !== null){
                clearTimeout(timer);
            }
            timer = setTimeout(fn,wait);
        }
    }
    /**
     * [domMove 动态拖拽]
     * @method domMove
     * @param  {[Element]} MoveDOM [想要动态拖拽Element]
     */
    function domMove(MoveDOM){
        var disX,disY, moveX,moveY, L, T, starX, starY, starXEnd, starYEnd;
        var canMove = false;
        var canTouch = true;
        function getDomDistance() {
            return {
                MoveDomLeft : parseFloat(MoveDOM.style.left.split('px')[0]),
                MoveDomRight : document.body.scrollWidth-parseFloat(MoveDOM.style.left.split('px')[0])-parseFloat(getComputedStyle(MoveDOM, null)['width'].split('px')[0])
            }

        }
        function rate(MoveDOM,a) {
            //  console.log(a);
            canTouch = true
            if(a === ''){
                MoveDOM.style.transition = ''
                MoveDOM.style.transform = ''
            }else {
                MoveDOM.style.transition = '.5s';
                if(a==90){
                    debounce(function (a) {
                        MoveDOM.style.left='0px'
                        document.getElementById('neko').style.transform = 'translateX(-50%)'// rotate(90deg)
                    },1000)()

                }else if(a==-90){
                    debounce(function (a) {
                        MoveDOM.style.left=document.body.scrollWidth-parseFloat(getComputedStyle(MoveDOM, null)['width'].split('px')[0])+'px'
                        document.getElementById('neko').style.transform = 'translateX(50%)'// rotate(-90deg)
                    },1000)()
                }
            }

        }
        function down(e){
            canMove = true ;
            //取消动画
            rate(MoveDOM,'')
            e.preventDefault();//阻止触摸时页面的滚动，缩放
            if(e.touches){
                disX = e.touches[0].clientX - MoveDOM.offsetLeft;
                disY = e.touches[0].clientY - MoveDOM.offsetTop;
                starX = e.touches[0].clientX;
                starY = e.touches[0].clientY;
                console.log(disX+"+"+disY)
                console.log(MoveDOM.offsetLeft+"+"+MoveDOM.offsetTop)
            }else {
                disX = e.clientX - MoveDOM.offsetLeft;
                disY = e.clientY - MoveDOM.offsetTop;
                starX = e.clientX;
                starY = e.clientY;
                console.log(disX+"+"+disY)
            }

        }
        function move(e){
            if(canMove === true){
                if (e.touches){
                    L = e.touches[0].clientX - disX;
                    T = e.touches[0].clientY - disY;
                    starXEnd = e.touches[0].clientX - starX;
                    starYEnd = e.touches[0].clientY - starY;
                }else {
                    L = e.clientX - disX;
                    T = e.clientY - disY;
                    starXEnd = e.clientX - starX;
                    starYEnd = e.clientY - starY;
                }
                //console.log(L);
                if (L < 0) {
                    L = 0;
                } else if (L > document.documentElement.clientWidth - MoveDOM.offsetWidth) {
                    L = document.documentElement.clientWidth - MoveDOM.offsetWidth;
                }

                if (T < 0) {
                    T = 0;
                } else if (T > document.documentElement.clientHeight - MoveDOM.offsetHeight) {
                    T = document.documentElement.clientHeight - MoveDOM.offsetHeight;
                }
                moveX = L + 'px';
                moveY = T + 'px';
                console.log(moveX);
                MoveDOM.style.left = moveX;
                MoveDOM.style.top = moveY;
            }
        }
        function end(){
            let current = (new Date()).valueOf();
            if (current - timestamps < 150&&canTouch) {  //200ms内为短按
                alert('短按执行了')
                rate(MoveDOM,0,'right')
            } else {  //长按
                canMove = false
                canTouch = false
                console.log(canMove)
                if(getDomDistance().MoveDomLeft<=70){
                    rate(MoveDOM,90)
                }else if(getDomDistance().MoveDomRight<=70){
                    rate(MoveDOM,-90)
                }
            }

        }
        function start(){
            window.timestamps = (new Date()).valueOf();
        }
        MoveDOM.addEventListener('touchstart', function (e) {
            //取消动画
            rate(MoveDOM,'')
            //e.preventDefault();
            down(e);
            //开始计算点击时间
            start()
        });
        MoveDOM.addEventListener('mousedown', function (e) {
            //取消动画
            rate(MoveDOM,'')
            //e.preventDefault();
            down(e);
            //开始计算点击时间
            start()
        });
        window.addEventListener('mouseleave',function (e){
            canMove = false//关闭拖拽状态
            if(getDomDistance().MoveDomLeft<=40){
                rate(MoveDOM,90)
            }else if(getDomDistance().MoveDomRight<=40){
                rate(MoveDOM,-90)
            }
        })
        MoveDOM.addEventListener('mouseleave',function (e){
            canMove = false//关闭拖拽状态
            if(getDomDistance().MoveDomLeft<=40){
                rate(MoveDOM,90)
            }else if(getDomDistance().MoveDomRight<=40){
                rate(MoveDOM,-90)
            }

        })
        MoveDOM.addEventListener('touchmove', function (e) {
            move(e);
        });
        MoveDOM.addEventListener('mousemove', function (e) {
            move(e);
        });
        MoveDOM.addEventListener("mouseup",function(e){
            end()
        },false),
            MoveDOM.addEventListener("touchend",function(e){
                end()
            },false)
        MoveDOM.addEventListener('transitionend', function(){
            canTouch = true
        }, false)
    }
    //执行函数
    domMove(MoveDom)
}