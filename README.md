# PROJECT

In the second half of the course, you will use the principles of object-oriented programming to implement a project in Natural Language Processing (NLP). This will be a group project where you will be assigned into groups of three or four and you will have to collaboratively work on the project. This will be a challenging project because apart from the technical requirements of the project, you will also need to handle the human aspects i.e., work with a human to complete a task. As Computer Scientists, we are better at working with computers but it is necessary to learn how to work with other humans on the same task. 

Apart from the NLP system, each project should have - 

1. Backend - A database that implements CRUD (Create, Read, Update, and Delete) operations relevant to the specific project.
2. Frontend - A UI that allows users to interact with the application. 

We will look at specific technologies to implement the frontend and backend.

Each team can select one of the three NLP problems - 

1. Classification - In this type of project, teams will create a system that allow users to add a document and their application should predict a label, classifying the document. The documents and the labels is dependent on the domain and the setting. You will be using either Bag of Words model or TF-IDF to extract features from the model and then use Naive Bayes Classifier to predict a label. You can use the following links to search for datasets - [Papers With Code](https://paperswithcode.com/task/text-classification), [Hugging Face Datasets](https://huggingface.co/datasets?modality=modality:text&sort=trending), [UCI Repository](https://archive.ics.uci.edu/datasets?skip=0&take=10&sort=desc&orderBy=NumHits&search=&Types=Text). You should be able to find other plethora of datasets by searching for "Text Classification Datasets". Note that we will implement the NLP logic in Java. You wil be provided with sufficient guides to implement this system.  

2. Similarity Search - In this type of project, teams will create a system that allow users to search for documents that are related to a specific document that users provide. Similar to the previous problem, you will use a frequency based method (Bag of Words/TF-IDF) to extract features from the provided document and use simple techniques to identify similar features of documents in the dataset.

3. Summarization - In this type of project, teams will create a system that allows users to upload a document and get a summary of that document. This task will also use a frequency based method to extract features and use rudimentary techniques to identify important sentences from the document that convey the meaning of the document.  More resources to come but you can start your search with "Extract Summarization Datasets".

The teams need to select a problem they want to work with and think of a domain they want to apply it to. For example, 

* if you want to work on a *classification* problem, a possible domain you can work in is review classification. You can create a system where a user is allowed to add a review for a movie and your system predicts if the review is positive or negative.

* if you want to work on a *similarity search* problem, a possible domain is academic research. You can create a system that allow users to add a research paper and search for similar papers in the database.

* if you want to work on a *summarization* problem, academic research can again be used in this domain. You can create a system that allow users to add a research paper and provides extractive summary of the paper. 

The above examples are designed to help you think how you can apply one of the three NLP problems to a domain that your team is interested in. Note that all these NLP problems will be implemented in Java without using a NLP library (in most cases). Our goal is not to create the most advanced NLP system but develop a good understanding of how develop systems with a backend and UI that solve important NLP problems in specific domains. Your team will be provided with sufficient resources to implement the core technologies of these systems. 

The teams have been pre-assigned and you are not allowed to change your teammates. Every team is sent the names and email address of all their members. 

## Deliverables

Following are the deliverables

1. [Project Proposal (Tuesday, October 29th 2024, 11:59 PM)](#project-proposal-5-of-the-overall-course-grade)

2. [GitHub Repo (Friday, November 1st 2024, 11:59 PM)](#github-repo)

3. [Backend (Friday, November 15th 2024, 11:59 PM)](#backend-5-of-the-overall-course-grade)

4. [Frontend (Friday, December 5th 2024, 11:59 PM)](#frontend-5-of-the-overall-course-grade)

5. [Final Presentation (Tuesday December 10th and Thursday 12th 2024 during class period)](#final-presentation-5-of-the-overall-course-grade)

6. [Final commit (Friday, December 13th 2024, 11:59 PM)](#final-commit-10-of-the-overall-course-grade)



### Project Proposal (5% of the overall course grade)

Send a 1-page proposal describing the NLP problem and domain you want to implement for the project. In this proposal, you need to describe the NLP problem that you want to implement, the domain you are interested , the rationale behind choosing this domain, and describing the key features you want to implement. You also need to attach a UML diagram of the system to describe its components and how you plan to implement them. You also need to describe the dataset you plan to use for your system. 

### GitHub Repo

Ensure you have created a GitHub repo and invited me and Jacob as a collaborators (along with all your teammates). We will use GitHub to monitor equal contributions among team-members. 

### Backend (5% of the overall course grade)

You have a working backend that allows you to create, read, update, and delete entries from your database. These entries will be dependent on your domain.

### Frontend (5% of the overall course grade)

You have a frontend that is connected with the backend and are able to do the CRUD operations.

### Final Presentation (5% of the overall course grade)

A 10-minute team presentation **along with a demo** describing the problem, the components in the system, and UI.

### Final Commit (10% of the overall course grade)

The final commit should have the system implemented that will allow me or Jacob to run and interact with the system locally on our machines. 
