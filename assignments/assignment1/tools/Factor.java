import java.io.*;

class Factor
{
	public static void main(String args[])
	{
		if (args.length == 0)
		{
			System.out.println("Usage: java Factor [integer]");
			System.exit(0);
		}
		int n = Integer.parseInt(args[0]);
		System.out.println("Factor of "+n+" include:");
		if ((n % 2) == 0)
		{
			System.out.print(2+" ");
		}

		for (int i = 3; i <= n; i+=1)
		{
			if ((n % i) == 0)
				System.out.print(i+" ");
		}
		System.out.println();
	}
}
