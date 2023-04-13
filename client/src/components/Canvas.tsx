// CANVAS COMPONENT //
import { useRef, useEffect, useState } from "react";

const Canvas = () => {
  const canvasRef = useRef<HTMLCanvasElement | null>(null);

  // HIDE AFTER RESIZE //
  /*
   Decided to hide the canvas if the user starts resizing the window to enhance performance
  */
  const [hideCanvas, setHideCanvas] = useState(false);
  const [dimensions, setDimensions] = useState({
    height: window.innerHeight,
    width: window.innerWidth,
  });
  useEffect(() => {
    function handleResize() {
      setHideCanvas(true);
      // setDimensions({
      //   height: window.innerHeight,
      //   width: window.innerWidth,
      // });
    }
    window.addEventListener("resize", handleResize);
  });

  // SNAKE CLASS //
  class snake {
    canvas:HTMLCanvasElement;
    context:CanvasRenderingContext2D;
    x:number;
    y:number;
    size:number;
    followerLength:number;
    direction:string;
    gap:number;
    lastX: number[];
    lastY: number[];
    
    constructor(canvas:HTMLCanvasElement, context:CanvasRenderingContext2D, x:number, y:number, size:number, length:number, direction:string, color:string) {
      this.canvas = canvas;
      this.context = context;
      this.x = x;
      this.y = y;
      this.size = size;
      this.followerLength = length - 1;
      this.direction = direction;
      this.gap = size / 2;
      this.lastX = [];
      this.lastY = [];
      // set color
      this.context.fillStyle = color;
    }
    draw() {
      this.context.fillRect(this.x, this.y, this.size, this.size);
    }
    drawFollowers() {
      for (let i = 0; i < this.followerLength; i++) {
        this.context.fillRect(
          this.lastX[i],
          this.lastY[i],
          this.size,
          this.size
        );
      }
      if (this.lastX.length >= this.followerLength) {
        this.lastX.shift();
        this.lastY.shift();
      }
    }
    move() {
      // Save last position for the following blocks
      this.lastX.push(this.x);
      this.lastY.push(this.y);
      // Move
      if (this.direction === "left") {
        this.x -= this.size + this.gap;
      } else if (this.direction === "up") {
        this.y -= this.size + this.gap;
      } else if (this.direction === "right") {
        this.x += this.size + this.gap;
      } else if (this.direction === "down") {
        this.y += this.size + this.gap;
      }
      // Change direction
      const changeDirectionValue = Math.floor(Math.random() * 2);
      if (changeDirectionValue === 0) {
        this.turnLeft();
      }
      if (changeDirectionValue === 1) {
        this.turnRight();
      }
      // Stay inside canvas
      if (this.y < -100) this.direction = "down";
      if (this.y > this.canvas.height + 100) this.direction = "up";
      if (this.x < -100) this.direction = "right";
      if (this.x > this.canvas.width + 100) this.direction = "left";
    }
    turnLeft() {
      if (this.direction === "left") {
        this.direction = "up";
      } else if (this.direction === "up") {
        this.direction = "right";
      } else if (this.direction === "right") {
        this.direction = "down";
      } else if (this.direction === "down") {
        this.direction = "left";
      }
    }
    turnRight() {
      if (this.direction === "left") {
        this.direction = "down";
      } else if (this.direction === "up") {
        this.direction = "left";
      } else if (this.direction === "right") {
        this.direction = "up";
      } else if (this.direction === "down") {
        this.direction = "right";
      }
    }
  }

  // CREATE OBJECTS //
  const createObjects = (context:CanvasRenderingContext2D) => {
    const snakeArray = [];
    let snakeAmount;
    if(dimensions.width < 800) {
      snakeAmount = 0;
    } else {
      snakeAmount = 20;
    }
    const directions = ["up", "down", "left", "right"];
    const color = "#000";
    const size = 10;
    for (let i = 0; i < snakeAmount; i++) {
      const length = Math.floor(Math.random() * 60) + 50;
      const direction =
        directions[Math.floor(Math.random() * directions.length)];
      let posX = Math.floor(Math.random() * context.canvas.width);
      posX = Math.ceil(posX / 15) * 15;      
      let posY = Math.floor(Math.random() * context.canvas.width);
      posY = Math.ceil(posY / 15) * 15;
      // Add to snake array
      snakeArray.push(
        new snake(
          context.canvas,
          context,
          posX,
          posY,
          size,
          length,
          direction,
          color
        )
      );
    }
    return snakeArray;
  };

  // DRAW FUNCTION //
  const draw = (context:CanvasRenderingContext2D, snakeArray:snake[]) => {
    context.clearRect(0, 0, context.canvas.width, context.canvas.height);
    snakeArray.forEach((particle:snake) => particle.draw());
    snakeArray.forEach((particle:snake) => particle.drawFollowers());
    snakeArray.forEach((particle:snake) => particle.move());
  };

  // MAIN LOOP //
  useEffect(() => {
    const fps = 40;
    const canvas:HTMLCanvasElement|null = canvasRef.current;
    let context:any;
    if(canvas) context = canvas.getContext("2d");
    let animationFrameId:number;
    // Create objects
    const snakeArray = createObjects(context);
    // Loop
    const render = () => {
      draw(context, snakeArray);
      setTimeout(() => {
        animationFrameId = window.requestAnimationFrame(render);
      }, 1000 / fps);
    };
    render();
    // Clean-up
    return () => {
      window.cancelAnimationFrame(animationFrameId);
    };
  }, []);

  // RETURN //
  if (hideCanvas) {
    return <></>;
  }
  return (
    <canvas
      width={dimensions.width}
      height={dimensions.height - 52} // minus navbar height
      ref={canvasRef}
    />
  );
};

export default Canvas;
