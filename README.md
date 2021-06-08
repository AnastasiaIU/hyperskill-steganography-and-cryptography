# hyperskill-steganography-and-cryptography
The program hides and reads hidden messages from pictures.

Objectives for the project:

Stage 1.

The program should read input strings (commands) in a loop.

Your program should:

Print the message Task (hide, show, exit): and read standard input in a loop.  

If the input is "exit", print "Bye!" and exit.  
If the input is "hide", print "Hiding message in image." and return to the input loop.  
If the input is "show", print "Obtaining message from image." and return to the input loop.  
If any other string is input, then print "Wrong task: [input String]" and return to the input loop.

Stage 2.

When the user inputs the command "hide", the program should prompt them for an input image filename with the message "Input image file:" and an output image filename with the message "Output image file:". These should be used for reading the input image file and writing the output image file, respectively.

After reading the filenames, the program should print the following messages: "Input Image: [input filename]" and "Output Image: [output filename]".

When the input image is read, the least significant bit for each color (Red, Green, and Blue) is set to 1. The resulting image will be saved with the provided output image filename in the PNG format.

A proper method should be applied so that the I/O exceptions do not terminate the program. In such cases, an exception message should be printed and the program should return to the command loop.

Stage 3.

When the hide command is given, the program gets the input and output image filenames as in the previous stage. Then, it prompts the user for the secret message by printing "Message to hide:".

The message should be converted to an array of bytes. Then, 3 bytes with the values 0, 0, 3 should be added at the end of the array.

The program should check that the image size is adequate for holding the Bytes array. If not, it should print an error message with the text "The input image is not large enough to hold this message." and return to the menu.

Each bit of this Bytes Array will be saved at the position of the least significant bit of the blue color of each pixel. The output image should be saved in the PNG format.

When the show command is given, the program asks for the image filename (previously saved with the hidden message) by printing "Input image file:". The image will be opened and the Bytes Array will be reconstructed bit by bit; the program will stop reading it when the bytes with the values 0, 0, 3 are encountered.

The last 3 bytes (values 0, 0, 3) should be removed from the end of the Bytes Array. Then, the message should be restored as a String from the Bytes Array.

The program should print "Message:" and then the message itself on a new line.

Stage 4.

When the hide command is given and the secret message is input, the user should be prompted for a password with the message "Password:".

The program reads the password string and converts it to a Bytes Array. The first message byte will be XOR encrypted using the first password byte, the second message byte will be XOR encrypted with the second password byte, and so on. If the password is shorter than the message, then after the last byte of the password, the first byte of the password should be used again.

Three Bytes with values 0, 0, 3 should be added to the encrypted Bytes Array. If the image size is adequate for holding the Bytes array, the result is hidden in the image like in the previous stage.

When the show command is given and the filename is input, the user should be prompted for the password with the message "Password:". The image should open and the encrypted Bytes Array should be reconstructed just like in the previous stage; the program stops reading it when the bytes with the values 0, 0, 3 are found. The last three bytes should be removed and the encrypted Bytes Array should be decrypted using the password. Finally, the message should be restored to the String type, and the program should print "Message:" and then the message itself on a new line.
