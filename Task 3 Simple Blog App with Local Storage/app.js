/**
 * Simple Blog App with Local Storage
 * This application allows users to create, read, and delete blog posts
 * with data persisted in the browser's local storage.
 */

// DOM Elements
const postForm = document.getElementById('post-form');
const postTitleInput = document.getElementById('post-title');
const postContentInput = document.getElementById('post-content');
const postsContainer = document.getElementById('posts-container');
const postCountElement = document.getElementById('post-count');
const emptyState = document.getElementById('empty-state');
const postTemplate = document.getElementById('post-template');

// Blog Post Class
class BlogPost {
    constructor(id, title, content, date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }
}

// Blog App Class
class BlogApp {
    constructor() {
        this.posts = [];
        this.loadFromLocalStorage();
        this.renderPosts();
        this.setupEventListeners();
    }

    /**
     * Set up event listeners for the application
     */
    setupEventListeners() {
        // Form submission event
        postForm.addEventListener('submit', (e) => {
            e.preventDefault();
            this.addPost();
        });

        // Event delegation for delete buttons
        postsContainer.addEventListener('click', (e) => {
            if (e.target.closest('.btn-delete')) {
                const postElement = e.target.closest('.post');
                const postId = postElement.dataset.id;
                this.deletePost(postId);
            }
        });
    }

    /**
     * Add a new blog post
     */
    addPost() {
        const title = postTitleInput.value.trim();
        const content = postContentInput.value.trim();

        if (title && content) {
            const newPost = new BlogPost(
                Date.now().toString(), // Generate unique ID using timestamp
                title,
                content,
                new Date()
            );

            // Add to posts array
            this.posts.unshift(newPost); // Add to beginning of array
            
            // Save to local storage
            this.saveToLocalStorage();
            
            // Render the updated posts
            this.renderPosts();
            
            // Reset the form
            postForm.reset();
            
            // Focus on title input for next post
            postTitleInput.focus();
        }
    }

    /**
     * Delete a blog post by ID
     * @param {string} id - The ID of the post to delete
     */
    deletePost(id) {
        // Confirm before deleting
        if (confirm('Are you sure you want to delete this post?')) {
            // Filter out the post with the given ID
            this.posts = this.posts.filter(post => post.id !== id);
            
            // Save to local storage
            this.saveToLocalStorage();
            
            // Render the updated posts
            this.renderPosts();
        }
    }

    /**
     * Render all blog posts to the DOM
     */
    renderPosts() {
        // Clear the posts container
        postsContainer.innerHTML = '';
        
        // Update post count
        postCountElement.textContent = this.posts.length;
        
        // Show empty state if no posts
        if (this.posts.length === 0) {
            postsContainer.appendChild(emptyState);
            return;
        }
        
        // Render each post
        this.posts.forEach(post => {
            const postElement = this.createPostElement(post);
            postsContainer.appendChild(postElement);
        });
    }

    /**
     * Create a DOM element for a blog post
     * @param {BlogPost} post - The blog post object
     * @returns {HTMLElement} - The post element
     */
    createPostElement(post) {
        // Clone the template
        const postElement = document.importNode(postTemplate.content, true).querySelector('.post');
        
        // Set post ID as data attribute
        postElement.dataset.id = post.id;
        
        // Set post content
        postElement.querySelector('.post-title').textContent = post.title;
        postElement.querySelector('.post-content').textContent = post.content;
        
        // Format and set the date
        const formattedDate = this.formatDate(new Date(post.date));
        postElement.querySelector('.post-date').textContent = formattedDate;
        
        return postElement;
    }

    /**
     * Format a date object to a readable string
     * @param {Date} date - The date to format
     * @returns {string} - Formatted date string
     */
    formatDate(date) {
        const options = { 
            year: 'numeric', 
            month: 'long', 
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        };
        return date.toLocaleDateString('en-US', options);
    }

    /**
     * Save posts to local storage
     */
    saveToLocalStorage() {
        localStorage.setItem('blog-posts', JSON.stringify(this.posts));
    }

    /**
     * Load posts from local storage
     */
    loadFromLocalStorage() {
        const storedPosts = localStorage.getItem('blog-posts');
        if (storedPosts) {
            this.posts = JSON.parse(storedPosts);
        }
    }
}

// Initialize the blog app when the DOM is fully loaded
document.addEventListener('DOMContentLoaded', () => {
    new BlogApp();
});