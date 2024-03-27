// CRUDS interface

public interface iCruds
{
	cUserInterface[] fetchAll() throws Exception;
	int addRecord(cUserInterface record) throws Exception;
}