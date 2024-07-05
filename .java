require('dotenv').config();
const pool = require('./server/config/database');
const express = require('express');
const cors = require('cors');
const userRouter = require('./server/api/users/user.router');
const questionRouter = require('./server/api/question/question.router');
const answerRouter = require('./server/api/answer/answer.router');

const app = express();
const port = process.env.PORT || 3000;

app.use(cors());
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

pool.getConnection((err, connection) => {
  if (err) {
    console.error('Error connecting to database:', err);
    return;
  }
  console.log('Connected to database');

  // Release the connection back to the pool
  connection.release();

  //Start the server
  app.listen(port, () => {
    console.log(`Listening at http://localhost:${port}`);
  });
});

app.use('/api/users', userRouter);
app.use('/api/question', questionRouter);
app.use('/api/answer', answerRouter);

