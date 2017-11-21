//use of modified/simplefied Command Pattern
import java.util.*;

public class Opdr8_Robot
{
	public static void main(String[] args) 
	{
		//Test program
		Robot robot1 = new Robot();
		Robot robot2 = new Robot(0,1, "North");
		Robot robot3 = new Robot(1,0, "West");

		System.out.print("Robot 1: ");
		robot1.printState();
		System.out.print("Robot 2: ");
		robot2.printState();
		System.out.print("Robot 3: ");
		robot3.printState();

		System.out.println("\nProgramming robot2");
		robot2.goLeft();
		robot2.goLeft();
		robot2.goForward();
		robot2.goBackward(3);
		robot2.goRight();
		robot2.goForward(2);
		robot2.printExecuteList();
		System.out.print("Current position of robot2: ");
		robot2.printState();
		robot2.execute();
		robot2.printState();
		robot2.execute();
	}
	
}

class Robot
{
	private int xCoord;
	private int yCoord;
	private String direction;
	private ArrayList<Command> commands = new ArrayList<Command>();
	
	public Robot()
	{
		this(0, 0, "North");
	}

	public Robot(int x, int y, String direction)
	{
		xCoord = x;
		yCoord = y;
		this.direction = direction;
	}

	public void printState()
	{
		System.out.println("Now facing " + '"' + direction + '"' + " at (" + xCoord + "," + yCoord + ")");
	}

	public void printExecuteList()
	{
		if(commands.size() > 0)
		{
			for(int i = 0; i < commands.size(); i++)
			{
					System.out.println("\t"+ (i+1) + ": " + commands.get(i).getClass().getName());
			}
		}
		else
		{
			System.out.println("The command list is empty");
		}
	}

	public void execute()
	{
		if(commands.size() > 0)
		{
			for(int i = 0; i < commands.size(); i++)
			{
				commands.get(i).execute();
				System.out.print("\tExecuting " + commands.get(i).getClass().getName() + "...");
				printState();
			}
			
			System.out.println("\tAll commands are executed");
			commands.clear();
		}
		else
		{
			System.out.println("The command list is empty");
		}
		
	}

	public void goLeft()
	{
		commands.add(new TurnLeftCommand(this));
	}
	protected void turnLeft()
	{
		switch(direction.toUpperCase())
		{
			case "NORTH": 
				direction = "West";
				break;
			case "EAST": 
				direction = "North";
				break;
			case "SOUTH":
				direction = "East";
				break;
			case "WEST":
				direction = "South";
				break;
			default:
				direction = "North";
				break;
		}
	}

	public void goRight()
	{
		commands.add(new TurnRightCommand(this));
	}
	protected void turnRight()
	{
		switch(direction.toUpperCase())
		{
			case "NORTH": 
				direction = "East";
				break;
			case "EAST": 
				direction = "South";
				break;
			case "SOUTH":
				direction = "West";
				break;
			case "WEST":
				direction = "East";
				break;
			default:
				direction = "North";
				break;
		}
	}

	public void goForward()
	{
		goForward(1);
	}
	public void goForward(int speed)
	{
		commands.add(new MoveForwardCommand(this,speed));
	}
	protected void forward(int speed)
	{
		if(speed < 1)
		{
			speed = 1;
		}
		else if(speed > 3)
		{
			speed = 3;
		}

		switch(direction.toUpperCase())
		{
			case "NORTH": 
				yCoord += speed;
				break;
			case "EAST": 
				xCoord += speed;
				break;
			case "SOUTH":
				yCoord -= speed;
				break;
			case "WEST":
				xCoord -= speed;
				break;
			default:
				direction = "North";
				yCoord += speed;
				break;
		}
	}

	public void goBackward()
	{
		goBackward(1);
	}
	public void goBackward(int speed)
	{
		commands.add(new MoveBackwardCommand(this,speed));
	}
	protected void backward(int speed)
	{
		if(speed < 1)
		{
			speed = 1;
		}
		else if(speed > 3)
		{
			speed = 3;
		}

		switch(direction.toUpperCase())
		{
			case "NORTH": 
				yCoord -= speed;
				break;
			case "EAST": 
				xCoord -= speed;
				break;
			case "SOUTH":
				yCoord += speed;
				break;
			case "WEST":
				xCoord += speed;
				break;
			default:
				direction = "North";
				yCoord -= speed;
				break;
		}
	}
}

interface Command
{
	public void execute();
}

class TurnLeftCommand implements Command 
{
	Robot robot;

	public TurnLeftCommand(Robot robot)
	{
		this.robot = robot;
	}
	public void execute()
	{
		robot.turnLeft();
	}
}

class TurnRightCommand implements Command 
{
	Robot robot;

	public TurnRightCommand(Robot robot)
	{
		this.robot = robot;
	}
	public void execute()
	{
		robot.turnRight();
	}
}

class MoveForwardCommand implements Command 
{
	Robot robot;
	int speed;

	public MoveForwardCommand(Robot robot, int speed)
	{
		this.robot = robot;
		this.speed = speed;
	}
	public void execute()
	{
		robot.forward(speed);
	}
}

class MoveBackwardCommand implements Command 
{
	Robot robot;
	int speed;

	public MoveBackwardCommand(Robot robot, int speed)
	{
		this.robot = robot;
		this.speed = speed;
	}
	public void execute()
	{
		robot.backward(speed);
	}
}



