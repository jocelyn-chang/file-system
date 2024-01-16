import java.util.Iterator;
import java.util.ArrayList;

/**
 * FileStructure is a class that represents the linked structure that will store the information of the file objects
 * in the file system.
 * 
 * @author jocelynchang
 *
 */
public class FileStructure {
	/**
	 * Refers to the root node of the linked structure.
	 */
	private NLNode<FileObject> root;
	
	/**
	 * Creates a linked structure with the root node being the given file name.
	 * 
	 * @param fileObjectName  the name of the file to be the root node of the structure
	 * @throws FileObjectException  thrown when there is a problem constructing the FileObject
	 */
	public FileStructure(String fileObjectName) throws FileObjectException {
		//initialize the FileObject with the file name given and create a root node with it
		FileObject fileObject = new FileObject(fileObjectName);
		root = new NLNode<FileObject> (fileObject, null);
		
		//runs the recursive algorithm if the root node's data is a folder
		if (fileObject.isDirectory() == true) {
			structureHelp(root);
		}
	}
	
	/**
	 * A recursive algorithm to help traverse the individual files in each folder to create nodes.
	 * 
	 * @param r  a node with files that will be traversed if it is a folder
	 */
	private void structureHelp(NLNode<FileObject> r) {
		//assign the data of the node to a variable
		FileObject f = r.getData();
		
		//get an iterator containing all the FileObjects inside f
		Iterator<FileObject> fileIterator = f.directoryFiles();
		
		//if f is a folder iterate through the files of f
		if (f.isDirectory() == true) {
			while (fileIterator.hasNext()) {
				//create a new node for each file
				NLNode<FileObject> n = new NLNode<FileObject> (fileIterator.next(), r);
				//adds the new node as the initial parameter node's child
				r.addChild(n);
				//sets the new node's parent as the initial parameter node
				n.setParent(r);
				//invoke the algorithm again with the new node as the parameter
				structureHelp(n);
			}
		}
	}
	
	/**
	 * Returns the root node.
	 * 
	 * @return  the root node
	 */
	public NLNode<FileObject> getRoot() {
		return root;
	}
	
	/**
	 * Returns an iterator with all the files found with the specific file type.
	 * 
	 * @param type  the file type of the files to be found
	 * @return  a String iterator with the absolute paths to every file with the specified file type
	 */
	public Iterator<String> filesOfType(String type) {
		//initialize an empty String ArrayList
		ArrayList<String> fileNames = new ArrayList<String>();
		
		//create a String iterator with the empty String ArrayList
		Iterator<String> nameIterator = fileNames.iterator();
		
		//return the iterator provided by the recursive algorithm
		nameIterator = fileTypeHelp(getRoot(), type, fileNames);
		return nameIterator;
	}
	
	/**
	 * A recursive algorithm that returns an iterator with all the absolute paths of the files with the matching file type.
	 * 
	 * @param r  a node with files that will be traversed through if it is a folder
	 * @param type  the file type of the files to be found
	 * @param fileNames  an ArrayList of the absolute paths of the files with the specified file type
	 * @return  a String iterator with the absolute paths of the files with the specified file type
	 */
	private Iterator<String> fileTypeHelp(NLNode<FileObject> r, String type, ArrayList<String> fileNames) {
		//assign the data of the node to a variable
		FileObject f = r.getData();
		
		//checks if the data is a file
		if (f.isFile()) {
			//checks if the absolute path ends with the specified file type and then adds it to the ArrayList
			if (f.getLongName().endsWith(type)) {
				fileNames.add(f.getLongName());
			}
		}
		
		//checks if the data is a folder
		else if (f.isDirectory()) {
			//gets an iterator containing all the children of the parameter node
			Iterator<NLNode<FileObject>> childIterator = r.getChildren();
			//iterates through each file object
			while (childIterator.hasNext()) {
				//invoke the algorithm again with the new node as the parameter
				NLNode<FileObject> n = childIterator.next();
				fileTypeHelp(n, type, fileNames);
			}
		}
		//returns the iterator
		return fileNames.iterator();
	}
	
	/**
	 * Finds the file with the specified name and returns the absolute path of the first instance of the file.
	 * 
	 * @param name  the name of the file to be found
	 * @return  a String of the absolute path of the file
	 */
	public String findFile(String name) {
		//returns the String provided by the recursive algorithm with the root node and given file name as the parameters
		return finderHelper(root, name);
	}
	
	private String finderHelper(NLNode<FileObject> r, String name) {
		//assign the data of the node to a variable
		FileObject f = r.getData();
		
		//checks if the given node's data is a file
		if (f.isFile()) {
			//checks if the file name matches the parameter
			if (f.getName().equals(name)) {
				//returns the file's absolute path
				return r.getData().getLongName();
			}
		} 
		
		//checks if the given node's data is a folder
		else if (f.isDirectory()) {
			//gets an iterator containing all the children of the parameter node
			Iterator<NLNode<FileObject>> childIterator = r.getChildren();
			//iterates through each file object
			while (childIterator.hasNext()) {
				NLNode<FileObject> n = childIterator.next();
				//invoke the algorithm again and checks if there is a returned absolute path and returns it
				String absolutePath = finderHelper(n, name);
				if (!absolutePath.equals("")) {
					return absolutePath;
				}
			}
		}
		//returns an empty string if the absolute path has not been found
		return "";
	}
}
