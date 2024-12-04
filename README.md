Lora-Symlink
A Java program to create symlinks for LORAs and generate variants.

This program is in its beta phase and aims to create variants using symbolic links (symlinks). With this, it is possible to have multiple predefined configurations for the same LORA file without the need for redundant copies. Additionally, it allows the base LORA file and the generated variants to be stored on different disks.

Features:
Create multiple LORA variants: Easily manage variants using symlinks.
Customizable Trigger Words: You can create LORA variants with custom trigger words.
Multiple Storage Locations: Store the LORA base file and its variants on different drives.
Simple JSON Configuration: Each variant is configured through a simple .JSON file.
Workflow

1. Download and Select the Base LORA
![01](https://github.com/user-attachments/assets/23da95fd-d1c3-4bec-8950-891dc08e90ce)
After downloading the LORA file, you can select the base folder where you want to save it. This folder can be located in your image generator's LORA directory or on any other hard drive.

2. Configure the Base LORA File
![02](https://github.com/user-attachments/assets/b80198fc-0975-4b67-9b1e-8570ec135be5)
When opening the program, the Directory section should contain the path to the base LORA file. In the Name section, enter the file name (it must have the .safetensors format).

Once you click Load, the screen will display options to generate a .JSON file from the base LORA. Fill in the necessary data if required.

3. Variant Configuration
![03](https://github.com/user-attachments/assets/2d684ed4-ec67-49f2-90be-b8738978502f)
Next, you will see input fields for creating variants. The first section is for variant type A. The default directory will be the same as the base file's directory, but you can change it if needed.

Enter the variant name (e.g., if the variant name is "V" and the base file is "Cordelia.safetensors", the variant will be named "Cordelia-V.safetensors"). You can choose from three predefined variant types, and data for variants B and C are stored in a log for future use if necessary.

4. Add Variants
![04](https://github.com/user-attachments/assets/7b37c674-8e1d-4398-ba05-c28921fc780d)
You will find three buttons: "Add Variant 1", "Add Variant 2", and "Add Variant 3". Each of these buttons opens a screen to configure the corresponding .JSON data for the variant.

The .JSON files are editable later if needed. When you press a button, the name and directory will be automatically assigned based on the selected variant.

5. Generate Files
![05](https://github.com/user-attachments/assets/a6d8f4b5-7899-4e65-bc91-a9fa2cda7d18)
![07](https://github.com/user-attachments/assets/19459615-7cff-4839-b9f4-543fdd8f5723)
Once you've set up everything, pressing the "Generate Files" button will create the symlinks and the .JSON files for all the configured variants.

6. Preview Variants
![06](https://github.com/user-attachments/assets/a162f991-abd5-478c-bf2d-d89e3f1cec49)
![08](https://github.com/user-attachments/assets/b847e968-baa4-47b3-a479-80c357b0df10)
Once the symlinks are created, you can generate images with the program and create previews for each variant.

7. File Storage Locations
![09](https://github.com/user-attachments/assets/bdb34aad-55c8-448b-84df-91989dc7bd15)
As shown in this screen, the base LORA file and symlinks can be stored on different hard drives. However, it is also possible to store them in the same folder if deemed convenient.

NOTICE: If the name or path of the base LORA file is changed, the symlinks will stop working.

Requirements:
Java (version 8 or higher)
Operating System: Compatible with Windows, macOS, and Linux.
