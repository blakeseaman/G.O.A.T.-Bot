scanner in = new Scanner(new File("mapping.csv"));

int numlines= -1;
int linelength= -1;

while(in.hasNextLine())
{
	numLines++;
	string currrLine = in.nextLine();
	if(lineLength==-1)
	{
		lineLngth=currLine.length();
	}
	else if(lineLength != currLine.length())
	{
		System.out.println("You have an error");
		return;
	}
	else
	{
		System.out.println("error");
		return;
	}
	
}
	in.close();
	
	in = new Scanner(new File("mapping.csv"));

	char[][] map = new char[numLines][lineLength];
	int i = 0;
while(in.hasNextLine())
{
	map[i]=(in.nextLine()).split(",");
	i++;
}
