# F-Droid Submission Guide

This guide explains how to submit the Motorola Power Saver app to F-Droid.

## 1. Fork the F-Droid Data Repository

1. Go to the F-Droid data repository on GitLab: https://gitlab.com/fdroid/fdroiddata
2. Log in to your GitLab account (or create one if you don't have it).
3. Click the "Fork" button in the top right corner to create a copy of the repository under your own account.

## 2. Clone Your Fork Locally

1. Open your terminal.
2. Clone your forked repository:
   ```bash
   git clone https://gitlab.com/<your-username>/fdroiddata.git
   ```
3. Navigate into the cloned directory:
   ```bash
   cd fdroiddata
   ```

## 3. Copy the Metadata File

1. In the `fdroiddata` directory, there is a folder named `metadata`.
2. Locate the metadata file we generated for this app: `fdroid/com.moto.extremesaver.yml` in our project repository.
3. Copy this file into the `metadata` directory of your `fdroiddata` clone:
   ```bash
   cp /path/to/Motorola-Power-Saver/fdroid/com.moto.extremesaver.yml metadata/
   ```

## 4. Commit and Push Changes

1. Stage the new file:
   ```bash
   git add metadata/com.moto.extremesaver.yml
   ```
2. Commit the change with a descriptive message:
   ```bash
   git commit -m "Add Motorola Power Saver (com.moto.extremesaver)"
   ```
3. Push the commit to your fork on GitLab:
   ```bash
   git push origin master
   ```

## 5. Create a Merge Request

1. Go back to your forked repository on GitLab in your web browser.
2. GitLab should show a prompt to "Create merge request" for your recently pushed branch. Click it.
3. Ensure the target branch is `master` on `fdroid/fdroiddata`.
4. Fill out the merge request template, verifying that the details match our app's information.
5. Submit the merge request.

## 6. Review Process

The F-Droid maintainers or their automated CI pipeline (fdroidserver) will review the submission.
- They will attempt to build the app from the source code specified in the metadata file.
- If the build fails or there are other issues, they will leave comments on your merge request.
- You will need to address these issues by updating your code or the metadata file, committing the changes, and pushing them to your fork (which will automatically update the merge request).

Once everything is correct, they will merge your request, and the app will appear on F-Droid!